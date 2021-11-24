package iris.backend_service.configurations;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
@RequiredArgsConstructor
public class CentralConfiguration {

	private final @NonNull ObjectMapper objectMapper;

	@Value("classpath:central-configuration.json")
	Resource configResource;

	private ConfigurationData configurationData;

	@PostConstruct
	private void initialize() throws IOException {

		configurationData = objectMapper.registerModule(new JavaTimeModule())
				.readValue(configResource.getInputStream(), ConfigurationData.class);
	}

	Optional<HdProxyConfig> getHdProxyConfigFor(String client) {

		return configurationData.hdProxyConfigs.stream()
				.filter(it -> client.equals(it.hdName))
				.findFirst();
	}

	record ConfigurationData(List<HdProxyConfig> hdProxyConfigs) {}

	record HdProxyConfig(String hdName, String abbreviation, String proxySubDomain) {}
}
