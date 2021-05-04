package iris.demo.checkin_app;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

@SpringBootApplication
public class IrisDemoCheckinAppApplication {



	public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		Security.setProperty("crypto.policy", "unlimited");
		SpringApplication.run(IrisDemoCheckinAppApplication.class, args);

	}

}
