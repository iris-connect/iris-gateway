package iris.backend_service.messages.zammad;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import java.net.URI;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * @author Jens Kutzsche
 */
@ConfigurationProperties(prefix = "iris.messages.zammad")
@ConstructorBinding
@Data
public class ZammadProperties {

	private final @NonNull URI apiAddress;
	private final @NonNull String token;
	private final @NonNull String environment;
	private final @NonNull Map<String, String> customerIds;
	private final String group = "Users";

	public URI getTicketUri() {
		return apiAddress.resolve("/api/v1/tickets");
	}

	public URI getTagUri() {
		return apiAddress.resolve("/api/v1/tags/add");
	}
}
