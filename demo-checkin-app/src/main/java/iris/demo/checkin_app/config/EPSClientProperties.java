package iris.demo.checkin_app.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties("iris")
@Getter
public class EPSClientProperties {

	private final @NotNull String backendServiceEndpoint;

}
