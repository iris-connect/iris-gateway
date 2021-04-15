package de.healthIMIS.iris.irislocationservice.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.stream.Collectors;


@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "iris.allowed-providers")
public class AllowedProviders {

    private final List<Provider> providers;

    public Provider findByProviderId(String providerId) {
        var configurationList = providers.stream()
                .filter(it -> StringUtils.equals(it.getId(), providerId))
                .collect(Collectors.toList());
        var listSize = configurationList.size();
        if (listSize != 1) {
            return null;
        }
        return configurationList.get(0);
    }

    @Data
    public static class Provider {
        String id;
        String name;
    }
}
