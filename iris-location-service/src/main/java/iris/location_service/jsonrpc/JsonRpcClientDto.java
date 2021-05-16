package iris.location_service.jsonrpc;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@RequiredArgsConstructor
public class JsonRpcClientDto {

	@JsonProperty("Name")
	private @NotNull String name;

}
