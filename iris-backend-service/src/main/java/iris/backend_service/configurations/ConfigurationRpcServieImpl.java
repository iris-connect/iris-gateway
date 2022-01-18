package iris.backend_service.configurations;

import static java.lang.String.*;

import iris.backend_service.jsonrpc.JsonRpcClientDto;
import iris.backend_service.messages.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class ConfigurationRpcServieImpl implements ConfigurationRpcService {

	private final @NotNull CentralConfiguration config;
	private final @NotNull TokenProperties tokenProps;
	private final @NotNull AlertService alerts;

	@Override
	public Configuration getHdConfiguration(@Valid JsonRpcClientDto client) {

		var proxyConfig = config.getHdProxyConfigFor(client.getName())
				.orElseThrow(() -> {

					alerts.createAlertMessage("Missing HD configuration",
							String.format("Can't find a HD proxy configuration for client: %s", client.getName()));

					return new CentralConfigurationException(
							format("Can't find a health department proxy configuration for your client: %s", client.getName()));
				});

		log.debug("JSON-RPC - Get HD configuration for client: {}", client.getName());

		return new Configuration(proxyConfig.getAbbreviation(), proxyConfig.getProxySubDomain(), tokenProps.getCatSalt(),
				tokenProps.getDatSalt(), tokenProps.getCatLength(), tokenProps.getDatLength());
	}
}
