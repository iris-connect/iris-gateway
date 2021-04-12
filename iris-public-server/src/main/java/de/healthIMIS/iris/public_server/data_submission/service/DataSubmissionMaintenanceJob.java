package de.healthIMIS.iris.public_server.data_submission.service;

import de.healthIMIS.iris.public_server.data_submission.repository.DataSubmissionRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataSubmissionMaintenanceJob {

    @Autowired
    DataSubmissionRepository submissionRepository;

    @Autowired
    DataSubmissionService dataSubmissionService;

    @Scheduled(fixedDelay = 15000)
    void run() {

        log.info("Maintenance Job running...");
        dataSubmissionService.deleteSubmissionsAfterGraceTime();

    }

}
