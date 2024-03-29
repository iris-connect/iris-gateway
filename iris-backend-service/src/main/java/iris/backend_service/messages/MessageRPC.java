package iris.backend_service.messages;

import iris.backend_service.jsonrpc.JsonRpcClientDto;

import java.util.List;

import javax.validation.Valid;

import com.googlecode.jsonrpc4j.JsonRpcParam;

public interface MessageRPC {

	String postAlerts(
			@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client,
			@Valid @JsonRpcParam(value = "alertList") List<AlertDto> alertDtos);

	FeedbackResponseDto postFeedback(
			@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client,
			@Valid @JsonRpcParam(value = "feedback") FeedbackDto request);
}
