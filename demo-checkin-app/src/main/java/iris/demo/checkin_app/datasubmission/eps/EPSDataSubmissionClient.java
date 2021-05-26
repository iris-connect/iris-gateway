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

	private final static String TEMPORARY_PROVIDER_ID = "demo-app";

	public void postDataSubmissionGuests(DataSubmissionDto dataSubmissionDto, String hdEndpoint)
			throws IRISDataSubmissionException {

		try {
			var methodName = hdEndpoint + ".submitGuestList";
			rpcClient.invoke(methodName, dataSubmissionDto, JsonRPCStringResult.class);
		} catch (Throwable t) {
			throw new IRISDataSubmissionException(t);
		}

	}
}
