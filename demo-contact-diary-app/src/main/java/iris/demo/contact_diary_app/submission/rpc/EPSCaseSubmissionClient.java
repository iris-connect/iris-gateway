package iris.demo.contact_diary_app.submission.rpc;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import iris.demo.contact_diary_app.submission.rpc.dto.CaseSubmissionDto;
import iris.demo.contact_diary_app.submission.rpc.dto.JsonRPCStringResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EPSCaseSubmissionClient {

	private final JsonRpcHttpClient rpcClient;

	public void postCaseSubmission(CaseSubmissionDto submissionDto, String hdEndpoint)
			//TODO: throws IRISDataSubmissionException {
	{
		try {
			var methodName = hdEndpoint + ".submitContactAndEventData";
			rpcClient.invoke(methodName, submissionDto, JsonRPCStringResult.class);
		} catch (Throwable t) {
			throw new RuntimeException(t);//IRISDataSubmissionException(t);
		}

	}
}
