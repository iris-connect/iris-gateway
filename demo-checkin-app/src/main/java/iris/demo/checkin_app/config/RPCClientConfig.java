package iris.demo.checkin_app.config;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

@ConfigurationProperties("eps-client")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public class RPCClientConfig {

	private final @NonNull String clientUrl;

	@Bean
	public JsonRpcHttpClient rpcClient() throws MalformedURLException, NoSuchAlgorithmException, KeyManagementException {

		ObjectMapper jacksonObjectMapper = new ObjectMapper();
		jacksonObjectMapper.registerModule(new JavaTimeModule());

		JsonRpcHttpClient client = new JsonRpcHttpClient(
				jacksonObjectMapper,
				new URL(clientUrl),
				new HashMap<>());

		// NoopHostnameVerifier is needed because the internal eps address is not necessarily part of certs SAN
		client.setHostNameVerifier(new NoopHostnameVerifier());

		// This is SO! important
		client.setContentType("application/json");

		return client;
	}

}
