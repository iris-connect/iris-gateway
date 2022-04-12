package iris.backend_service.config;

import static java.util.stream.Collectors.*;

import iris.backend_service.configurations.ConfigurationRpcService;
import iris.backend_service.hd_search.HdSearchRpcService;
import iris.backend_service.jsonrpc.HealthRPC;
import iris.backend_service.jsonrpc.JsonRpcClientDto;
import iris.backend_service.locations.jsonrpc.LocationRPC;
import iris.backend_service.messages.MessageRPC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.ErrorResolver;
import com.googlecode.jsonrpc4j.spring.CompositeJsonServiceExporter;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JsonRpcEndpointsConfig {

	public static final String EPS_CLIENT_ATTRIBUTE = "EPS_CLIENT";

	public static final String ENDPOINT = "/backend-rpc";

	public static final int VALIDATION_ERROR_CODE = -32600;

	private final HealthRPC health;
	private final LocationRPC locations;
	private final MessageRPC message;
	private final ConfigurationRpcService configurations;
	private final HdSearchRpcService hdSearch;
	private final HttpServletRequest request;

	@Bean(name = ENDPOINT)
	public CompositeJsonServiceExporter jsonRpcServiceImplExporter() {

		var compositeJsonServiceExporter = new CompositeJsonServiceExporter();
		compositeJsonServiceExporter.setServiceInterfaces(new Class[] { HealthRPC.class, LocationRPC.class,
				MessageRPC.class, ConfigurationRpcService.class, HdSearchRpcService.class });
		compositeJsonServiceExporter.setServices(new Object[] { health, locations, message, configurations, hdSearch });
		compositeJsonServiceExporter.setAllowExtraParams(true);
		compositeJsonServiceExporter.setAllowLessParams(true);
		compositeJsonServiceExporter.setShouldLogInvocationErrors(false);
		compositeJsonServiceExporter.setErrorResolver(new JsonRpcErrorResolver());
		compositeJsonServiceExporter
				.setConvertedParameterTransformer((target, convertedParams) -> setEpsClientAsRequestAttribute(convertedParams));

		return compositeJsonServiceExporter;
	}

	private Object[] setEpsClientAsRequestAttribute(Object[] convertedParams) {

		Arrays.stream(convertedParams)
				.filter(JsonRpcClientDto.class::isInstance)
				.findFirst()
				.map(JsonRpcClientDto.class::cast)
				.map(JsonRpcClientDto::getName)
				.ifPresent(it -> request.setAttribute(EPS_CLIENT_ATTRIBUTE, it));

		return convertedParams;
	}

	class JsonRpcErrorResolver implements ErrorResolver {

		private final ObjectMapper objectMapper = new ObjectMapper();

		@Override
		public JsonError resolveError(Throwable t, Method method, List<JsonNode> arguments) {

			if (t instanceof ConstraintViolationException violExc) {

				var violations = violExc.getConstraintViolations();
				var data = createData(violations);

				log.warn("Invalid data from client {}: {}", request.getAttribute(EPS_CLIENT_ATTRIBUTE), data.errors());

				return createJsonError(VALIDATION_ERROR_CODE, "Invalid Data", data);
			}

			return null;
		}

		private JsonError createJsonError(int code, String message, InvalidDataErrors data) {
			return new JsonError(code, message, objectMapper.convertValue(data, JsonNode.class));
		}

		private InvalidDataErrors createData(Set<ConstraintViolation<?>> violations) {

			var errors = violations.stream()
					.collect(
							Collectors.groupingBy(it -> it.getPropertyPath().toString(),
									mapping(ConstraintViolation::getMessage, joining())));

			return new InvalidDataErrors(errors);
		}
	}

	record InvalidDataErrors(Map<String, String> errors) {}
}
