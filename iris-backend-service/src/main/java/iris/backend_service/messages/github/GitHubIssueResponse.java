package iris.backend_service.messages.github;

import lombok.Getter;

/**
 * This class represents the needed JSON structure of a GitHub response.
 * 
 * @author Ostfalia Gruppe 12
 * @author Jens Kutzsche
 */
@Getter
class GitHubIssueResponse {

	private String number;
}
