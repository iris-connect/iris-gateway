package iris.demo.contact_diary_app.submission.service;

import iris.demo.contact_diary_app.submission.rpc.EPSCaseSubmissionClient;
import iris.demo.contact_diary_app.submission.rpc.dto.CaseDataProvider;
import iris.demo.contact_diary_app.submission.rpc.dto.CaseSubmissionDto;
import iris.demo.contact_diary_app.submission.rpc.dto.Contacts;
import iris.demo.contact_diary_app.submission.rpc.dto.Events;
import iris.demo.contact_diary_app.submission.web.dto.SubmissionWebDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final EPSCaseSubmissionClient epsCaseSubmissionClient;

    public void submitCaseData(SubmissionWebDto submissionData) {
        log.info("Submit data: authToken={}; connAuthToken={}; zipCodeHd={}", submissionData.getDataAuthorizationToken(),
                submissionData.getConnectionAuthorizationToken(), submissionData.getZipCodeHealthDepartment());

        CaseSubmissionDto submissionDto = new CaseSubmissionDto(UUID.randomUUID(), //TODO: UUID.fromString(submissionData.getDataAuthorizationToken()),
                new Contacts(), new Events(), new CaseDataProvider());

        // TODO: Correct Endpoint
        epsCaseSubmissionClient.postCaseSubmission(submissionDto, submissionData.getConnectionAuthorizationToken());
    }
}
