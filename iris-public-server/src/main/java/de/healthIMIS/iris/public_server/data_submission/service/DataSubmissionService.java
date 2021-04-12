package de.healthIMIS.iris.public_server.data_submission.service;

import de.healthIMIS.iris.public_server.data_submission.model.DataSubmission;

import java.util.UUID;

public interface DataSubmissionService {

    int deleteDataSubmissionById(DataSubmission.DataSubmissionIdentifier id);

}
