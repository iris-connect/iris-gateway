package iris.backend_service.messages.github;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * This class holds the information about the connected git repo.
 * 
 * @author Ostfalia Gruppe 12
 * @author Jens Kutzsche
 */
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "iris.messages.github")
@Getter
public class GitHubProperties {

	private final @NonNull String personalAccessToken;
	private final @NonNull String repoOrg;
	private final @NonNull String repoName;
}
