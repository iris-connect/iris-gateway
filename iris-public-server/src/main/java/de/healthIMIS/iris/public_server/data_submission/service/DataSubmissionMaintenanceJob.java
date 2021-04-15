package de.healthIMIS.iris.public_server.data_submission.service;

import de.healthIMIS.iris.public_server.data_submission.repository.DataSubmissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableScheduling
@Profile("!inttest")
public class DataSubmissionMaintenanceJob {

    @Autowired
    DataSubmissionRepository submissionRepository;

    @Autowired
    DataSubmissionService dataSubmissionService;

    @Scheduled(fixedDelayString = "${iris.maintenance-job.interval}")
    void run() {

        log.info("Maintenance Job running...");
        dataSubmissionService.deleteSubmissionsAfterGraceTime();

    }

}
