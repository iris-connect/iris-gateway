package iris.feedback_service.feedback_submission;

import iris.feedback_service.feedback.web.DataFeedbackRequestDto;
import iris.feedback_service.feedback.web.DataFeedbackResponseDto;
import iris.feedback_service.config.GitConnectionProperties;
import iris.feedback_service.feedback_submission.model.GitIssueRequestDto;
import iris.feedback_service.feedback_submission.model.GitIssueResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/*
 * This class posts the git issue with infromation provided by the request of Iris-Client-Backend.
 * @author Ostfalia Gruppe 12
 */
@Service
public class GitAPIEndpointConnector {

	@Autowired
	private FeedbackRequestGitIssueConverter converter;

	@Autowired
	private GitConnectionProperties properties;

	@Autowired
	private RestTemplate rest;

	/*
	 * Posts the git issue.
	 * @throws RestClientException is thrown if connection to Git-API fails
	 * @param request object of Iris-Client-Backend
	 * @return response object containing the gitIssueId
	 */
	public DataFeedbackResponseDto sendRequestToGitAPI(DataFeedbackRequestDto request) throws RestClientException {

		var gitIssueDto = converter.convertRequestToGitIssue(request);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "token " + properties.getPersonalAccessToken());
		HttpEntity<GitIssueRequestDto> gitRequestDto = new HttpEntity<GitIssueRequestDto>(gitIssueDto, headers);

		var gitResponse = rest.exchange(
			"https://api.github.com/repos/{user}/{repoName}/issues",
			HttpMethod.POST,
			gitRequestDto,
			GitIssueResponseDto.class,
			properties.getUser(),
			properties.getRepoName());

//		log.trace("Submission Feedback - PUT to EPS is sent: {}", "testid");
		var result = new DataFeedbackResponseDto();
		result.setGitIssueId(gitResponse.getBody().getId());
		return result;
	}
}
