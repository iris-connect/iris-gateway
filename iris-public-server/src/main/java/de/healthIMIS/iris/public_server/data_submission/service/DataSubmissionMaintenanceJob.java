package de.healthIMIS.iris.public_server.data_submission.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@Slf4j
@AllArgsConstructor
public class DataSubmissionMaintenanceJob {

    private final @NotNull  DataSubmissionService dataSubmissionService;

    @Scheduled(fixedDelayString = "${iris.data-submission.maintenance-job-interval}")
    void run() {

        log.info("Maintenance Job running...");
        dataSubmissionService.deleteSubmissionsAfterGraceTime();

    }

}
