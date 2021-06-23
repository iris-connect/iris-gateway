package iris.demo.contact_diary_app.submission.web.dto;

import lombok.Data;

@Data
public class SubmissionWebDto {
    private String dataAuthorizationToken;
    private String connectionAuthorizationToken;
    private String zipCodeHealthDepartment;
}
