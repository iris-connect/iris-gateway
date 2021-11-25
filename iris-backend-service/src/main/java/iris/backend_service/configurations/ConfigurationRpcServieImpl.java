package iris.backend_service.configurations;

import static java.lang.String.*;

import iris.backend_service.jsonrpc.JsonRpcClientDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
class ConfigurationRpcServieImpl implements ConfigurationRpcService {

	private final @NotNull CentralConfiguration config;
	private final @NotNull TokenProperties tokenProps;

	@Override
	public Configuration getHdConfiguration(@Valid JsonRpcClientDto client) {

		var hdProxyConfig = config.getHdProxyConfigFor(client.getName())
				.orElseThrow(() -> {

					log.warn("JSON-RPC - Can't find a HD proxy configuration for client: {}", client.getName());

					return new CentralConfigurationException(
							format("Can't find a health department proxy configuration for your client: %s", client.getName()));
				});

		log.debug("JSON-RPC - Get HD configuration for client: {}", client.getName());

		return new Configuration(hdProxyConfig.abbreviation(), hdProxyConfig.proxySubDomain(), tokenProps.getCatSalt(),
				tokenProps.getDatSalt(), tokenProps.getCatLength(), tokenProps.getDatLength());
	}
}
