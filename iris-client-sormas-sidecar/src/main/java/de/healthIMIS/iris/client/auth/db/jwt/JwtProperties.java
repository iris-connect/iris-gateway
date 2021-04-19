package de.healthIMIS.iris.client.auth.db.jwt;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@Data
@ConfigurationProperties(prefix = "security.jwt")
@ConditionalOnProperty(
        value="security.auth",
        havingValue = "db"
)
public class JwtProperties {

  private String jwtSharedSecret;

}
