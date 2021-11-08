package iris.backend_service.config;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author Jens Kutzsche
 */
class JsonRpcErrorResolverTests {

	@Test
	@MethodSource()
	void resolveError_withConstraintViolationExceptions(ConstraintViolationException exc) {

		var sut = new JsonRpcEndpointsConfig.JsonRpcErrorResolver();

		var result = sut.resolveError(exc, null, null);

	}
}
