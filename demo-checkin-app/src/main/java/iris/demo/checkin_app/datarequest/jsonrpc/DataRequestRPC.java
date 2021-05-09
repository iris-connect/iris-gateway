package iris.demo.checkin_app.datarequest.jsonrpc;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import iris.demo.checkin_app.datarequest.model.dto.LocationDataRequestDto;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@JsonRpcService("/data-request-rpc")
public interface DataRequestRPC {

	String createDataRequest(@JsonRpcParam(value="dataRequest") LocationDataRequestDto locationDataRequestDto);


}
