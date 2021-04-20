package de.healthIMIS.iris.public_server.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

@ConstructorBinding
@AllArgsConstructor
@Getter
@ConfigurationProperties("iris.data-submission")
public class DataSubmissionProperties {

    private int graceTimeSeconds;

}
