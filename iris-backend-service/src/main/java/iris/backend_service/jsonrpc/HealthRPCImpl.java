package iris.backend_service.jsonrpc;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class HealthRPCImpl implements HealthRPC {

	private final @NotNull HealthEndpoint healthEndpoint;

	@Override
	public Status getHealth(JsonRpcClientDto client) {

		var status = healthEndpoint.health().getStatus();

		log.debug("JSON-RPC - Get health status for client: {} => result: {}", client.getName(), status);

		return status;
	}
}
