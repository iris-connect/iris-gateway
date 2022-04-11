package iris.backend_service.jsonrpc;

import iris.backend_service.core.validation.NoSignOfAttack;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class JsonRpcClientDto {

	@NotBlank
	@NoSignOfAttack
	String name;
}
