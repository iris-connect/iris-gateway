package iris.backend_service.messages.github;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * This class represents the needed JSON structure of a GitHub request.
 *
 * @author Ostfalia Gruppe 12
 * @author Jens Kutzsche
 */
@Data
@Builder
class GitHubIssueRequest {

	private @NonNull String title;
	private @NonNull String label;
	private @NonNull String body;
}
