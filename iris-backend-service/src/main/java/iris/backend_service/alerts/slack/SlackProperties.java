package iris.backend_service.alerts.slack;

import lombok.Data;
import lombok.NonNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * @author Jens Kutzsche
 */
@ConfigurationProperties(prefix = "iris.alerts.slack")
@ConstructorBinding
@Data
class SlackProperties {

	private final @NonNull String token;
	private final @NonNull String channel;
}
