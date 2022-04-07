package iris.demo.contact_diary_app.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@RequiredArgsConstructor
@ConfigurationProperties("proxy-service")
public class ProxyServiceConfig {
	private String port;
}
