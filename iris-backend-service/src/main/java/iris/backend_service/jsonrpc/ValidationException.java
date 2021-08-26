package iris.backend_service.jsonrpc;

import com.fasterxml.jackson.databind.JsonNode;
import com.googlecode.jsonrpc4j.JsonRpcClientException;

public class ValidationException extends JsonRpcClientException {

    public ValidationException(int code, String errorMessage, JsonNode data) {
        super(code,errorMessage,data);
    }
}