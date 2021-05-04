package iris.public_server;

import static org.assertj.core.api.Assertions.*;

import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.RestTemplate;

@IrisWebIntegrationTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
class X509AuthenticationIntegrationTests {

	private @LocalServerPort String port;
	private final ResourceLoader resources;
	private final RestTemplateBuilder builder;

	@Test
	void allowsAccessToHdResourceIfCertificatePresent() throws Exception {

		var response = createRestTemplate(true).getForEntity(
				"https://localhost:{port}/hd/data-submissions?departmentId={depId}&from={from}",
				String.class, port, UUID.randomUUID().toString(), Instant.now());

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void rejectsAccessToHdResourceIfNoneCertificatePresent() throws Exception {

		assertThatExceptionOfType(Forbidden.class)
				.isThrownBy(
						() -> createRestTemplate(false).getForEntity(
								"https://localhost:{port}/hd/data-submissions?departmentId={depId}&from={from}",
								String.class, port, UUID.randomUUID().toString(), Instant.now()));
	}

	RestTemplate createRestTemplate(boolean withKeystore) throws Exception {

		var password = "iris";

		var keyStore = resources.getResource("classpath:security/client/keystore.p12");
		var trustStore = resources.getResource("classpath:security/client/truststore.p12");

		var sslContext = new SSLContextBuilder()
				.loadTrustMaterial(trustStore.getURL(), password.toCharArray(), (chain, authType) -> false);

		if (withKeystore) {
			sslContext = sslContext.loadKeyMaterial(keyStore.getURL(), password.toCharArray(), password.toCharArray());
		}

		var socketFactory = new SSLConnectionSocketFactory(sslContext.build(), NoopHostnameVerifier.INSTANCE);
		var httpClient = HttpClientBuilder.create().setSSLSocketFactory(socketFactory).build();

		var restTemplate = builder.build();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

		return restTemplate;
	}
}
