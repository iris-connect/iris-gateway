package iris.demo.checkin_app.datasubmission.eps;

import iris.demo.checkin_app.datasubmission.eps.dto.DataSubmissionDto;
import iris.demo.checkin_app.datasubmission.exceptions.IRISDataSubmissionException;
import iris.demo.checkin_app.datasubmission.model.dto.GuestListDto;
import lombok.AllArgsConstructor;


import org.springframework.stereotype.Service;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

@Service
@AllArgsConstructor
public class EPSDataSubmissionClient {

	private final JsonRpcHttpClient rpcClient;

	private final static String TEMPORARY_PROVIDER_ID = "f002f370-bd54-4325-ad91-1aff3bf730a5";

	public void postDataSubmissionGuests(GuestListDto guestSubmission, String hdEndpoint) throws IRISDataSubmissionException {
		var payload = DataSubmissionDto.builder().providerId(TEMPORARY_PROVIDER_ID).guestList(guestSubmission);

		try {
			var methodName = hdEndpoint + ".submitGuestList";
			rpcClient.invoke(methodName, payload, String.class);
		} catch (Throwable t) {
			throw new IRISDataSubmissionException(t);
		}

	}
}
