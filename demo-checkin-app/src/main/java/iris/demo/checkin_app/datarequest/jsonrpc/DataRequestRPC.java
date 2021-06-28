package iris.demo.checkin_app.datarequest.jsonrpc;

import iris.demo.checkin_app.datarequest.model.dto.LocationDataRequestDto;

import javax.validation.Valid;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

@JsonRpcService("/data-request-rpc")
public interface DataRequestRPC {

	String createDataRequest(
			@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client,
			@JsonRpcParam(value = "dataRequest") LocationDataRequestDto locationDataRequestDto);

}
