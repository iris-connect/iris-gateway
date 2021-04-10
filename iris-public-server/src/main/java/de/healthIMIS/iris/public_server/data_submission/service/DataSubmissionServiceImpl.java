package de.healthIMIS.iris.public_server.data_submission.service;

import de.healthIMIS.iris.public_server.data_submission.model.DataSubmission;
import de.healthIMIS.iris.public_server.data_submission.repository.DataSubmissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Slf4j
public class DataSubmissionServiceImpl implements DataSubmissionService {

    private final DataSubmissionRepository submissions;

    public DataSubmissionServiceImpl(DataSubmissionRepository submissions) {
        this.submissions = submissions;
    }

    @Override
    public int deleteDataSubmissionById(UUID id) {

        DataSubmission submissionToDelete = submissions.findById(id).orElse(null);
        if (submissionToDelete == null) return 0;

        int deleteOrphaned = deleteOrphanedSubmissions(submissionToDelete);
        submissions.delete(submissionToDelete);
        log.info("Deleted submission "+submissionToDelete.getId());

        return 1 + deleteOrphaned;
    }

    private int deleteOrphanedSubmissions(DataSubmission refrenceSubmission) {

        Streamable<DataSubmission> orphanedSubmissions = submissions.findAllByDepartmentIdAndRequestedIsBeforeAndIdNot(
                refrenceSubmission.getDepartmentId(),
                LocalDateTime.now().minusSeconds(2),
                refrenceSubmission.getId());

        return (int) orphanedSubmissions.stream().peek(submission -> {
            submissions.delete(submission);
            log.info("Deleted orphaned submission "+submission.getId());
        }).count();

    }
}
