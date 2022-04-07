package iris.demo.contact_diary_app;

import java.security.Security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;

@SpringBootApplication
@ConfigurationPropertiesScan
public class IrisDemoContactDiaryApp {

	public static void main(String[] args) {
		Security.setProperty("crypto.policy", "unlimited");
		SpringApplication.run(IrisDemoContactDiaryApp.class, args);
	}

	@Bean
	public static AutoJsonRpcServiceImplExporter autoJsonRpcServiceImplExporter() {
		return new AutoJsonRpcServiceImplExporter();
	}

}
