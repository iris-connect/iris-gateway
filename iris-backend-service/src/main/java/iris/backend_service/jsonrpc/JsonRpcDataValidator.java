package iris.backend_service.jsonrpc;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JsonRpcDataValidator {

	private final Validator validator;

	public <T> void validateData(T data) {

		validateItemToViolations(data).ifPresent(it -> {
			throw new ConstraintViolationException(it);
		});
	}

	private <T> Optional<Set<ConstraintViolation<T>>> validateItemToViolations(T data) {

		var constraintViolations = validator.validate(data);

		return constraintViolations.isEmpty() ? Optional.empty() : Optional.of(constraintViolations);
	}
}
