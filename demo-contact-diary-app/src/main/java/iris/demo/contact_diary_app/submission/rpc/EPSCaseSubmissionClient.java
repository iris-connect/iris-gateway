package iris.demo.contact_diary_app.submission.rpc;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import iris.demo.contact_diary_app.config.ProxyServiceConfig;
import iris.demo.contact_diary_app.config.RPCClientConfig;
import iris.demo.contact_diary_app.submission.rpc.dto.CaseSubmissionDto;
import iris.demo.contact_diary_app.submission.rpc.dto.JsonRPCStringResult;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EPSCaseSubmissionClient {

	private final ProxyServiceConfig proxyServiceConfig;
	private final ObjectProvider<JsonRpcHttpClient> rpcClientProvider;

	public JsonRPCStringResult postCaseSubmission(CaseSubmissionDto submissionDto, String hdEndpoint) {
		try {
			String methodName = "submitContactAndEventData";
			String protocol = "https";
			String port = proxyServiceConfig.getPort();
			if(hdEndpoint.startsWith("localhost")) {
				protocol = "http";
				port = "8092";
			}
			JsonRpcHttpClient rpcClient = rpcClientProvider.getObject(protocol + "://" + hdEndpoint + ":" + port + "/data-submission-rpc");
			return rpcClient.invoke(methodName, submissionDto, JsonRPCStringResult.class);

		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
