package iris.public_server.data_submission.service;

import org.springframework.data.util.Streamable;

import iris.public_server.data_submission.model.DataSubmission;
import iris.public_server.department.Department;

import java.time.Instant;

public interface DataSubmissionService {

    void deleteDataSubmissionById(DataSubmission.DataSubmissionIdentifier id);

    Streamable<DataSubmission> getSubmissionsForDepartmentFrom(Department.DepartmentIdentifier departmentId);

    void deleteSubmissionsAfterGraceTime();
}
