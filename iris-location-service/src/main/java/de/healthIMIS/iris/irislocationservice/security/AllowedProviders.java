package de.healthIMIS.iris.irislocationservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class AllowedProviders {

    @Value("#{${iris.allowed-providers}}")
    Map<String, String> providers;

}
