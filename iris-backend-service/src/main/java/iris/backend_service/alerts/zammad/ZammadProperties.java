package iris.backend_service.alerts.zammad;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * @author Jens Kutzsche
 */
@ConfigurationProperties(prefix = "iris.alerts.zammad")
@ConstructorBinding
@Data
public class ZammadProperties {

	private String apiAddress;
	private String group;
	private String customer;
}
