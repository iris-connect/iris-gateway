package iris.backend_service.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.googlecode.jsonrpc4j.ErrorData;
import com.googlecode.jsonrpc4j.ErrorResolver;
import com.googlecode.jsonrpc4j.JsonRpcClientException;

import java.lang.reflect.Method;
import java.util.List;

import static com.googlecode.jsonrpc4j.ErrorResolver.JsonError.ERROR_NOT_HANDLED;

public class JsonRpcErrorResolver implements ErrorResolver {

    public JsonError resolveError(Throwable t, Method method, List<JsonNode> arguments) {

        if (t.getClass() == JsonRpcClientException.class) {
            return new JsonError(
                    ((JsonRpcClientException) t).getCode(),
                    t.getMessage(),
                    ((JsonRpcClientException) t).getData());
        }

        return new JsonError(ERROR_NOT_HANDLED.code, t.getMessage(), new ErrorData(t.getClass().getName(), t.getMessage()));
    }

}
