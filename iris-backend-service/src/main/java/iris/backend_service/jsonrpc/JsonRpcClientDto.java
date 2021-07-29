package iris.backend_service.jsonrpc;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class JsonRpcClientDto {

	@NotNull
	String name;
}
