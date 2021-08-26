package iris.backend_service.config;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class InvalidDataError {

    Map<String, String> errors = new HashMap<>();

    public void addError(String identifier, String message) {
        errors.put(identifier, message);
    }

}
