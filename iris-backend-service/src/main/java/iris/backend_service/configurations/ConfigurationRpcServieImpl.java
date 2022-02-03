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

		var clientName = client.getName();

		var proxyConfig = config.getHdProxyConfigFor(clientName)
				.orElseThrow(() -> {

					alerts.createAlertMessage("Missing HD configuration",
							String.format("Can't find a HD proxy configuration for client: %s", clientName));

					return new CentralConfigurationException(
							format("Can't find a health department proxy configuration for your client: %s", clientName));
				});

		log.debug("JSON-RPC - Get HD configuration for client: {}", clientName);

		return new Configuration(proxyConfig.getAbbreviation(), proxyConfig.getProxySubDomain(), tokenProps.getCatSalt(),
				tokenProps.getDatSalt(), tokenProps.getCatLength(), tokenProps.getDatLength());
	}
}
