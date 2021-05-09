package iris.demo.checkin_app.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties("iris")
@Getter
public class EPSClientProperties {

	private final @NotNull String defaultHealthDepartmentEndpoint;

	private final @NotNull String defaultLocationServiceEndpoint;

}
