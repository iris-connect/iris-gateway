package iris.backend_service.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.JsonRpcClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Configuration
@RequiredArgsConstructor
public class JsonRpcDataValidator {

    private final Validator validator;

    ObjectMapper objectMapper = new ObjectMapper();

    public void validateData(Object data) {
        getItemError(data).ifPresent((error) -> {
            throw new JsonRpcClientException(-32600, "Invalid Request", objectMapper.convertValue(error, JsonNode.class));
        });
    }

    public <T> void validateData(List<T> data) {

        List<Object> invalidItems = new ArrayList<>();
        for (Object item : data) {
            getItemError(item).ifPresent((invalidItems::add));
        }
        if (!invalidItems.isEmpty())
            throw new JsonRpcClientException(-32600, "Invalid Request", objectMapper.convertValue(invalidItems, JsonNode.class));

    }

    private Optional<InvalidDataError> getItemError(Object data) {

        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(data);
        if (!constraintViolations.isEmpty()) {
            InvalidDataError itemErrors = new InvalidDataError();
            constraintViolations.stream().forEach(violation ->
                    itemErrors.addError(violation.getPropertyPath().toString(), violation.getMessage()));
            return Optional.of(itemErrors);
        }
        return Optional.ofNullable(null);
    }

}
