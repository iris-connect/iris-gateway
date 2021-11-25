package iris.backend_service.jsonrpc;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class JsonRpcClientDto {

	@NotBlank
	String name;
}
