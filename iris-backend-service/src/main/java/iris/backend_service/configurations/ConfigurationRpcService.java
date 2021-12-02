package iris.backend_service.configurations;

import iris.backend_service.jsonrpc.JsonRpcClientDto;

import javax.validation.Valid;

import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import com.googlecode.jsonrpc4j.JsonRpcParam;

public interface ConfigurationRpcService {

	@JsonRpcErrors({
			@JsonRpcError(exception = CentralConfigurationException.class, code = -1111)
	})
	Configuration getHdConfiguration(@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client);

	record Configuration(String abbreviation, String proxyUrl, String catSalt, String datSalt, int catLength,
			int datLength) {}
}
