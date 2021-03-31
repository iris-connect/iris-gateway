package de.healthIMIS.iris.public_server.config;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "providers")
public class AppProviderConfiguration {
  List<ProviderConfiguration> providers;

  public ProviderConfiguration findByProviderId(String providerId) {
    var configurationList =
        providers.stream()
            .filter(it -> StringUtils.equals(it.getId(), providerId))
            .collect(Collectors.toList());
    var listSize = configurationList.size();
    if (listSize != 1) {
      throw new RuntimeException(
          "Unexpected number of providers for id=" + providerId + " found=" + listSize);
    }
    return configurationList.get(0);
  }
}
