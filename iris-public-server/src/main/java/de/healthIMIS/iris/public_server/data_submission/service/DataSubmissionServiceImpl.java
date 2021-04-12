package de.healthIMIS.iris.public_server.data_submission.service;

import de.healthIMIS.iris.public_server.data_submission.model.DataSubmission;
import de.healthIMIS.iris.public_server.data_submission.repository.DataSubmissionRepository;
import de.healthIMIS.iris.public_server.department.Department;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Service
@Slf4j
public class DataSubmissionServiceImpl implements DataSubmissionService {

    @Autowired
    private DataSubmissionRepository submissions;

    @Override
    public int deleteDataSubmissionById(DataSubmission.DataSubmissionIdentifier submissionId) {

        DataSubmission submissionToDelete = submissions.findById(submissionId).orElse(null);
        if (submissionToDelete == null) return 0;

        int deleteOrphaned = deleteOrphanedSubmissions(submissionToDelete.getDepartmentId());
        submissions.delete(submissionToDelete);
        log.info("Deleted submission " + submissionToDelete.getId());

        return 1 + deleteOrphaned;

    }

    @Override
    public Streamable<DataSubmission> getSubmissionsForDepartmentFrom(Department.DepartmentIdentifier departmentId, LocalDateTime from) {

        Streamable<DataSubmission> dataSubmissions = submissions
                .findAllByDepartmentIdAndMetadataLastModifiedIsAfter(departmentId, from);

        setRequestedAt(dataSubmissions);

        return dataSubmissions;

    }

    @Override
    public void deleteSubmissionsAfterGraceTime() {

        Streamable<DataSubmission> orphanedSubmissions = submissions.findAllByRequestedAtBefore(
                LocalDateTime.now().minusSeconds(3));

        orphanedSubmissions.forEach(dataSubmission -> log.info("Delete submission after grace time "+dataSubmission.getId()));

        submissions.deleteAll(orphanedSubmissions.toList());

    }

    private void setRequestedAt(Streamable<DataSubmission> dataSubmissions) {

        dataSubmissions.forEach(submission -> {
            if (submission.getRequestedAt() == null)
                submission.setRequestedAt(LocalDateTime.now());
        });

        submissions.saveAll(dataSubmissions.toList());

    }

    private int deleteOrphanedSubmissions(Department.DepartmentIdentifier departmentIdentifier) {

        Streamable<DataSubmission> orphanedSubmissions = submissions.findAllByDepartmentIdAndRequestedAtIsBefore(
                departmentIdentifier,
                LocalDateTime.now().minusSeconds(3));

        return (int) orphanedSubmissions.stream().peek(submission -> {
            submissions.delete(submission);
            log.info("Deleted orphaned submission " + submission.getId());
        }).count();

    }
}
