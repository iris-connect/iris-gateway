package iris.feedback_service.config;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/*
 * This class holds the information about the connected git repo.
 * @author Ostfalia Gruppe 12
 */
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "git")
@Getter
public class GitConnectionProperties {

	private final @NonNull String personalAccessToken;
	private final @NonNull String user;
	private final @NonNull String repoName;
}
