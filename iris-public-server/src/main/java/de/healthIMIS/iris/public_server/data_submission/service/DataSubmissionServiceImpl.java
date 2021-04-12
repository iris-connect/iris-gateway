package de.healthIMIS.iris.public_server.data_submission.service;

import de.healthIMIS.iris.public_server.data_submission.model.DataSubmission;
import de.healthIMIS.iris.public_server.data_submission.repository.DataSubmissionRepository;
import de.healthIMIS.iris.public_server.department.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class DataSubmissionServiceImpl implements DataSubmissionService {

    private final DataSubmissionRepository submissions;

    public DataSubmissionServiceImpl(DataSubmissionRepository submissions) {
        this.submissions = submissions;
    }

    @Override
    public int deleteDataSubmissionById(DataSubmission.DataSubmissionIdentifier submissionId) {

        DataSubmission submissionToDelete = submissions.findById(submissionId).orElse(null);
        if (submissionToDelete == null) return 0;

        int deleteOrphaned = deleteOrphanedSubmissions(submissionToDelete.getDepartmentId());
        submissions.delete(submissionToDelete);
        log.info("Deleted submission "+submissionToDelete.getId());

        return 1 + deleteOrphaned;
    }

    private int deleteOrphanedSubmissions(Department.DepartmentIdentifier departmentIdentifier) {

        Streamable<DataSubmission> orphanedSubmissions = submissions.findAllByDepartmentIdAndRequestedIsBefore(
                departmentIdentifier,
                LocalDateTime.now().minusSeconds(2));

        return (int) orphanedSubmissions.stream().peek(submission -> {
            submissions.delete(submission);
            log.info("Deleted orphaned submission "+submission.getId());
        }).count();

    }
}
