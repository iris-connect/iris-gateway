package iris.demo.checkin_app;

import iris.demo.checkin_app.datarequest.jsonrpc.DataRequestRPCImpl;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;

@SpringBootApplication
@ConfigurationPropertiesScan
public class IrisDemoCheckinAppApplication {

	public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		Security.setProperty("crypto.policy", "unlimited");
		SpringApplication.run(IrisDemoCheckinAppApplication.class, args);

	}

	@Bean
	public static AutoJsonRpcServiceImplExporter autoJsonRpcServiceImplExporter() {
		return new AutoJsonRpcServiceImplExporter();
	}

	@Autowired
	public DataRequestRPCImpl locationRPC;

}
