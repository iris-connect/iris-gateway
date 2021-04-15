package de.healthIMIS.iris.public_server.data_submission.service;

import de.healthIMIS.iris.public_server.data_submission.model.DataSubmission;
import de.healthIMIS.iris.public_server.department.Department;
import org.springframework.data.util.Streamable;

import java.time.Instant;

public interface DataSubmissionService {

    void deleteDataSubmissionById(DataSubmission.DataSubmissionIdentifier id);

    Streamable<DataSubmission> getSubmissionsForDepartmentFrom(Department.DepartmentIdentifier departmentId, Instant fromString);

    void deleteSubmissionsAfterGraceTime();
}
