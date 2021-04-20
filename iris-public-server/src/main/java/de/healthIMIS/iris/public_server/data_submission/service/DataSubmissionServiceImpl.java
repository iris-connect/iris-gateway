package de.healthIMIS.iris.public_server.data_submission.service;

import de.healthIMIS.iris.public_server.config.DataSubmissionProperties;
import de.healthIMIS.iris.public_server.data_submission.model.DataSubmission;
import de.healthIMIS.iris.public_server.data_submission.repository.DataSubmissionRepository;
import de.healthIMIS.iris.public_server.department.Department.DepartmentIdentifier;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
@ConfigurationProperties(prefix = "iris.public-api", ignoreUnknownFields = false)
public class DataSubmissionServiceImpl implements DataSubmissionService {

    private final @NotNull DataSubmissionRepository submissions;
    private final @NotNull DataSubmissionProperties dataSubmissionProperties;

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
    public Streamable<DataSubmission> getSubmissionsForDepartmentFrom(DepartmentIdentifier departmentId, Instant from) {

        Streamable<DataSubmission> dataSubmissions = submissions
                .findAllByDepartmentIdAndMetadataLastModifiedIsAfter(departmentId, from);

        setRequestedAt(dataSubmissions);

        return dataSubmissions;

    }

    @Override
    public void deleteSubmissionsAfterGraceTime() {

        Streamable<DataSubmission> orphanedSubmissions = submissions.findAllByRequestedAtBefore(
                Instant.now().minusSeconds(dataSubmissionProperties.getGraceTimeSeconds()));

        orphanedSubmissions.forEach(dataSubmission ->
                log.info("Delete submission "+dataSubmission.getId()+" after "+dataSubmissionProperties.getGraceTimeSeconds()+"s grace time"));

        submissions.deleteAll(orphanedSubmissions.toList());

    }

    private void setRequestedAt(Streamable<DataSubmission> dataSubmissions) {

        dataSubmissions.forEach(submission -> {
            if (submission.getRequestedAt() == null)
                submission.setRequestedAt(Instant.now());
        });

        submissions.saveAll(dataSubmissions.toList());

    }
}
