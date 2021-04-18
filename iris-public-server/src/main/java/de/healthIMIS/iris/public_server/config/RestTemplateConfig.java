package de.healthIMIS.iris.public_server.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder)
          throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
    var sslContext = new SSLContextBuilder().loadTrustMaterial((chain, authType) -> true) // trust all server
            // certificates
            .build();
    var socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
    var httpClient = HttpClientBuilder.create().setSSLSocketFactory(socketFactory).build();
    var template = builder.build();
    template.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    return template;
  }
}
