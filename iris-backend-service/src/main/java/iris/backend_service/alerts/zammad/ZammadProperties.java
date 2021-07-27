package iris.backend_service.alerts.zammad;

import lombok.Data;
import lombok.NonNull;

import java.net.URI;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * @author Jens Kutzsche
 */
@ConfigurationProperties(prefix = "iris.alerts.zammad")
@ConstructorBinding
@Data
public class ZammadProperties {

	private final @NonNull URI apiAddress;
	private final @NonNull String token;
	private final @NonNull String environment;
	private final String group = "Users";

	public URI getTicketUri() {
		return apiAddress.resolve("/api/v1/tickets");
	}

	public URI getTagUri() {
		return apiAddress.resolve("/api/v1/tags/add");
	}
}
