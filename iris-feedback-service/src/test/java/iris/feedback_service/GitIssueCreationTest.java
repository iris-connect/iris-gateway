package iris.feedback_service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import iris.feedback_service.feedback.web.DataFeedbackRequestDto;
import iris.feedback_service.feedback_submission.FeedbackRequestGitIssueConverter;
import iris.feedback_service.feedback_submission.GitAPIEndpointConnector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class GitIssueCreationTest {

	private @InjectMocks FeedbackRequestGitIssueConverter converter;

	private @InjectMocks GitAPIEndpointConnector manager;

	private DataFeedbackRequestDto request;

	@BeforeAll
	public void init() {

		request = new DataFeedbackRequestDto();
		request.setTitle("That's a test tile!");
		request.setCategory("My Category");
		request.setComment("That's a test comment!");
		request.setName("Name Surname");
		request.setBrowserName("firefox");
	}

	@Test
	public void testGitIssueConverter() {

		var gitIssue = converter.convertRequestToGitIssue(request);
		assertThat(gitIssue.getTitle(), containsString(request.getTitle()));
		assertThat(gitIssue.getTitle(), containsString(request.getCategory()));
		assertThat(gitIssue.getBody(), containsString(request.getComment()));
		assertThat(gitIssue.getBody(), containsString(request.getName()));
		assertThat(gitIssue.getBody(), containsString(request.getBrowserName()));
	}
}
