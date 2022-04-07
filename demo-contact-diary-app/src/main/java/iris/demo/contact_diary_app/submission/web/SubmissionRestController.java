package iris.demo.contact_diary_app.submission.web;

import iris.demo.contact_diary_app.submission.service.SubmissionService;
import iris.demo.contact_diary_app.submission.web.dto.SubmissionWebDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/iris-gateway/cases")
public class SubmissionRestController {

	private final SubmissionService submissionService;

	@PostMapping
	public void postCaseSubmission(@RequestBody SubmissionWebDto submissionData) {
		submissionService.submitCaseData(submissionData);
	}
}
