package de.healthIMIS.iris.public_server.data_submission.service;

import de.healthIMIS.iris.public_server.data_submission.model.DataSubmission;
import de.healthIMIS.iris.public_server.department.Department;
import org.springframework.data.util.Streamable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface DataSubmissionService {

    int deleteDataSubmissionById(DataSubmission.DataSubmissionIdentifier id);

    Streamable<DataSubmission> getSubmissionsForDepartmentFrom(Department.DepartmentIdentifier departmentId, LocalDateTime fromString);

    void deleteSubmissionsAfterGraceTime();
}
