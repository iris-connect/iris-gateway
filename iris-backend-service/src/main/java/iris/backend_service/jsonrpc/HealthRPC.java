package iris.backend_service.jsonrpc;

import javax.validation.Valid;

import org.springframework.boot.actuate.health.Status;

import com.googlecode.jsonrpc4j.JsonRpcParam;

public interface HealthRPC {
	Status getHealth(@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client);
}
