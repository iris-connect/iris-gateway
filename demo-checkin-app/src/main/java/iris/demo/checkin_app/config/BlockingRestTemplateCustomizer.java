package iris.demo.checkin_app.config;

import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

	public ClientHttpRequestFactory clientHttpRequestFactory() throws NoSuchAlgorithmException, KeyManagementException {

		RequestConfig requestConfig = RequestConfig
				.custom()
				.setConnectionRequestTimeout(10000)
				.setSocketTimeout(10000)
				.build();

		TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
		SSLContext sslContext = null;
		try {
			sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		assert sslContext != null;
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
				NoopHostnameVerifier.INSTANCE);

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("https", sslsf)
				.register("http", new PlainConnectionSocketFactory())
				.build();

		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);
		connectionManager.setMaxTotal(100);
		connectionManager.setDefaultMaxPerRoute(20);

		CloseableHttpClient httpClient = HttpClients
				.custom()
				.setConnectionManager(connectionManager)
				.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
				.setDefaultRequestConfig(requestConfig)
				.setSSLHostnameVerifier(new NoopHostnameVerifier())
				.setSSLSocketFactory(sslsf)
				.build();

		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}

	@Override
	public void customize(RestTemplate restTemplate) {
		try {
			restTemplate.setRequestFactory(this.clientHttpRequestFactory());
		} catch (KeyManagementException | NoSuchAlgorithmException cause) {
			throw new BeanInitializationException(
					"Failed to disable HTTPS verification for REST template <" + restTemplate + ">!", cause);
		}
		restTemplate.getMessageConverters()
				.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
	}
}
