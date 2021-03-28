package de.healthIMIS.irisappapidemo;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class IrisAppApiDemoApplication {



	public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

		SpringApplication.run(IrisAppApiDemoApplication.class, args);

	}

}
