package de.healthIMIS.iris.client.auth.db.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security.jwt")
@ConditionalOnProperty(
        value="security.auth",
        havingValue = "db"
)
public class JwtConfiguration {

  private String keystorePassword;

  private String keystorePath;

  private String privateKeyPassword;

  private String keyAlias;

}
