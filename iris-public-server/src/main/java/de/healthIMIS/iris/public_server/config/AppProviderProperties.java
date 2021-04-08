package de.healthIMIS.iris.public_server.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "providers")
public class AppProviderProperties {

	private final List<Provider> providers;

	public Provider findByProviderId(String providerId) {
		var configurationList = providers.stream()
				.filter(it -> StringUtils.equals(it.getId(), providerId))
				.collect(Collectors.toList());
		var listSize = configurationList.size();
		if (listSize != 1) {
			throw new RuntimeException(
					"Unexpected number of providers for id=" + providerId + " found=" + listSize);
		}
		return configurationList.get(0);
	}

	@Data
	public static class Provider {
		String id;
		String dataRequestEndpoint;
	}
}
