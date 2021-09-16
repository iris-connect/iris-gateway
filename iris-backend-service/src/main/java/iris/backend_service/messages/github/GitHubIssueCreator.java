package iris.backend_service.messages.github;

import static org.springframework.http.MediaType.*;

import iris.backend_service.messages.Message;
import iris.backend_service.messages.MessageSendingResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * This class posts the issue to GitHub.
 * 
 * @author Ostfalia Gruppe 12
 * @author Jens Kutzsche
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GitHubIssueCreator {

	private final GitHubProperties properties;

	/**
	 * Posts the issue
	 * 
	 * @throws RestClientException is thrown if connection to Git-API fails
	 * @param message object of Iris-Client-Backend
	 * @return response object containing the gitIssueId
	 */
	public MessageSendingResult createIssue(Message message) throws RestClientException {

		if (StringUtils.equalsAny("DISABLED", properties.getPersonalAccessToken(), properties.getRepoOrg(),
				properties.getRepoName())) {
			return MessageSendingResult.disabled();
		}

		var responseOpt = WebClient.create("https://api.github.com")
				.post()
				.uri(uriBuilder -> uriBuilder.path("/repos/{repoOrg}/{repoName}/issues")
						.build(properties.getRepoOrg(), properties.getRepoName()))
				.headers(this::setHeaders)
				.bodyValue(createRequest(message))
				.retrieve()
				.toEntity(GitHubIssueResponse.class)
				.blockOptional();

		if (responseOpt.isEmpty()) {

			log.error("Message - GitHub - ticket could not be created => http call ends without result");
			return MessageSendingResult.withError();
		}

		var response = responseOpt.get();

		if (response.getStatusCode().is2xxSuccessful()) {

			log.debug("Message - GitHub - ticket created");
			return MessageSendingResult.ofIdFromSystem(responseOpt.get().getBody().getNumber());
		}

		log.error("Message - GitHub - ticket could not be created => Errors: {}", response.getBody());
		return MessageSendingResult.withError();
	}

	private void setHeaders(HttpHeaders headers) {
		headers.setContentType(APPLICATION_JSON);
		headers.setAccept(List.of(APPLICATION_JSON));
		headers.setBearerAuth(properties.getPersonalAccessToken());
	}

	private GitHubIssueRequest createRequest(Message message) {

		return GitHubIssueRequest.builder()
				.title(message.getTitle())
				.body(message.getText())
				.label("bug")
				.build();
	}
}
