package de.healthIMIS.iris.public_server.data_submission.service;

import de.healthIMIS.iris.public_server.config.DataSubmissionConfig;
import de.healthIMIS.iris.public_server.data_submission.model.DataSubmission;
import de.healthIMIS.iris.public_server.data_submission.repository.DataSubmissionRepository;
import de.healthIMIS.iris.public_server.department.Department;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Service
@Slf4j
@AllArgsConstructor
@ConfigurationProperties(prefix = "iris.public-api", ignoreUnknownFields = false)
public class DataSubmissionServiceImpl implements DataSubmissionService {

    private final @NotNull DataSubmissionRepository submissions;
    private final @NotNull DataSubmissionConfig dataSubmissionConfig;

    @Override
    public void deleteDataSubmissionById(DataSubmission.DataSubmissionIdentifier submissionId) {

        log.debug("Deleting submission " + submissionId);

        DataSubmission submissionToDelete = submissions.findById(submissionId).orElse(null);
        if (submissionToDelete == null)
        	return;

        submissions.delete(submissionToDelete);
        log.info("Deleted submission " + submissionToDelete.getId());
    }

    @Override
    public Streamable<DataSubmission> getSubmissionsForDepartmentFrom(Department.DepartmentIdentifier departmentId, Instant from) {

        Streamable<DataSubmission> dataSubmissions = submissions
                .findAllByDepartmentIdAndMetadataLastModifiedIsAfter(departmentId, from);

        setRequestedAt(dataSubmissions);

        return dataSubmissions;

    }

    @Override
    public void deleteSubmissionsAfterGraceTime() {

        Streamable<DataSubmission> orphanedSubmissions = submissions.findAllByRequestedAtBefore(
                Instant.now().minusSeconds(dataSubmissionConfig.getGraceTimeSeconds()));

        orphanedSubmissions.forEach(dataSubmission ->
                log.info("Delete submission "+dataSubmission.getId()+" after "+dataSubmissionConfig.getGraceTimeSeconds()+"s grace time"));

        submissions.deleteAll(orphanedSubmissions.toList());

    }

    private void setRequestedAt(Streamable<DataSubmission> dataSubmissions) {

        dataSubmissions.forEach(submission -> {
            if (submission.getRequestedAt() == null)
                submission.setRequestedAt(Instant.now());
        });

        submissions.saveAll(dataSubmissions.toList());

    }

    private int deleteOrphanedSubmissions(Department.DepartmentIdentifier departmentIdentifier) {

        Streamable<DataSubmission> orphanedSubmissions = submissions.findAllByDepartmentIdAndRequestedAtIsBefore(
                departmentIdentifier,
                Instant.now().minusSeconds(3));

        return (int) orphanedSubmissions.stream().peek(submission -> {
            submissions.delete(submission);
            log.info("Deleted orphaned submission " + submission.getId());
        }).count();

    }
}
