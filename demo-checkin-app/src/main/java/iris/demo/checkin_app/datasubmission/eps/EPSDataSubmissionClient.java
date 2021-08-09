package iris.demo.checkin_app.datasubmission.eps;

import iris.demo.checkin_app.datasubmission.eps.dto.DataSubmissionDto;
import iris.demo.checkin_app.datasubmission.exceptions.IRISDataSubmissionException;
import iris.demo.checkin_app.searchindex.eps.dto.JsonRPCStringResult;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

@Service
@AllArgsConstructor
public class EPSDataSubmissionClient {

	private final JsonRpcHttpClient rpcClient;

	public String postDataSubmissionGuests(DataSubmissionDto dataSubmissionDto, String hdEndpoint)
			throws IRISDataSubmissionException {

		JsonRPCStringResult result;
		try {
			var methodName = hdEndpoint + ".submitGuestList";
			 result = rpcClient.invoke(methodName, dataSubmissionDto, JsonRPCStringResult.class);
		} catch (Throwable t) {
			throw new IRISDataSubmissionException(t);
		}
		return result.toString();
	}
}
