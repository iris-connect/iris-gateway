package iris.backend_service.configurations;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
@RequiredArgsConstructor
public class CentralConfiguration {

	private final @NonNull ObjectMapper objectMapper;

	@Value("classpath:masterdata/central-configuration.json")
	Resource configBaseResource;

	@Value("${central-configuration.profil_resource}")
	Resource configProfilResource;

	private ConfigurationData configurationData;

	@PostConstruct
	private void initialize() throws IOException {

		var configurationBase = objectMapper.registerModule(new JavaTimeModule())
				.readValue(configBaseResource.getInputStream(), ConfigurationData.class);

		var updater = objectMapper.readerForUpdating(configurationBase);
		configurationData = updater.readValue(configProfilResource.getInputStream());
	}

	public Optional<String> getHdNameFor(String rkiCode) {

		return configurationData.hdConfigs.entrySet().stream()
				.filter(it -> StringUtils.equals(it.getValue().rkiCode, rkiCode))
				.map(Entry::getKey)
				.findFirst();
	}

	Optional<ProxyConfig> getHdProxyConfigFor(String client) {

		return Optional.ofNullable(configurationData.hdConfigs.get(client))
				.map(HdConfig::getProxyConfig);
	}

	record ConfigurationData(@JsonMerge Map<String, HdConfig> hdConfigs) {}

	@Data
	static class HdConfig {

		String rkiCode;
		ProxyConfig proxyConfig;
	}

	@Data
	static class ProxyConfig {

		String abbreviation;
		String proxySubDomain;
	}
}
