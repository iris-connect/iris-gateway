package iris.demo.contact_diary_app.submission.service;

import iris.demo.contact_diary_app.submission.bootstrap.ContactsLoader;
import iris.demo.contact_diary_app.submission.bootstrap.DataProviderLoader;
import iris.demo.contact_diary_app.submission.bootstrap.EventsLoader;
import iris.demo.contact_diary_app.submission.rpc.EPSCaseSubmissionClient;
import iris.demo.contact_diary_app.submission.rpc.dto.CaseSubmissionDto;
import iris.demo.contact_diary_app.submission.web.dto.SubmissionWebDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubmissionService {
	private final DataProviderLoader dataProviderLoader;
	private final ContactsLoader contactsLoader;
	private final EventsLoader eventsLoader;
	private final EPSCaseSubmissionClient epsCaseSubmissionClient;

	public void submitCaseData(SubmissionWebDto submissionData) {
		log.info("Submit data: authToken={}; connAuthToken={}; zipCodeHd={}", submissionData.getDataAuthorizationToken(),
				submissionData.getConnectionAuthorizationToken(), submissionData.getZipCodeHealthDepartment());

		CaseSubmissionDto submissionDto = new CaseSubmissionDto(UUID.fromString(submissionData.getDataAuthorizationToken()),
				contactsLoader.getContacts(), eventsLoader.getEvents(), dataProviderLoader.getDataProvider());

		var result = epsCaseSubmissionClient.postCaseSubmission(submissionDto,
				submissionData.getConnectionAuthorizationToken());
		log.info("Submit result: {}", result);
	}
}
