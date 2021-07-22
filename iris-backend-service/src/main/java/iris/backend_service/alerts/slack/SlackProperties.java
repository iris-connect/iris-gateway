package iris.backend_service.alerts.slack;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * @author Jens Kutzsche
 */
@ConfigurationProperties(prefix = "iris.alerts.slack")
@ConstructorBinding
@Data
public class SlackProperties {

	private String apiAddress;
	private String group;
	private String customer;
}
