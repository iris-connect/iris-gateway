package iris.demo.contact_diary_app.submission.rpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CaseSubmissionDto {
    private UUID dataAuthorizationToken;
    private Contacts contacts;
    private Events events;
    private CaseDataProvider dataProvider;
}
