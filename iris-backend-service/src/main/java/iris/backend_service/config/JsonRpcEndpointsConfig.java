package iris.backend_service.config;

import static java.util.stream.Collectors.*;

import iris.backend_service.configurations.ConfigurationRpcService;
import iris.backend_service.hd_search.HdSearchRpcService;
import iris.backend_service.jsonrpc.HealthRPC;
import iris.backend_service.locations.jsonrpc.LocationRPC;
import iris.backend_service.messages.MessageRPC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
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
import com.googlecode.jsonrpc4j.JsonRpcInterceptor;
import com.googlecode.jsonrpc4j.ProxyUtil;
import com.googlecode.jsonrpc4j.spring.JsonServiceExporter;

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
	public JsonServiceExporter jsonRpcServiceImplExporter() {

		Object[] services = {
				health,
				locations,
				message,
				configurations,
				hdSearch };
		Class<?>[] serviceInterfaces = {
				HealthRPC.class,
				LocationRPC.class,
				MessageRPC.class,
				ConfigurationRpcService.class,
				HdSearchRpcService.class };

		// create the composite service
		var service = ProxyUtil.createCompositeServiceProxy(getClass().getClassLoader(), services, serviceInterfaces,
				false);

		var jsonServiceExporter = new JsonServiceExporter();
		jsonServiceExporter.setService(service);
		jsonServiceExporter.setAllowExtraParams(true);
		jsonServiceExporter.setAllowLessParams(true);
		jsonServiceExporter.setShouldLogInvocationErrors(false);
		jsonServiceExporter.setErrorResolver(new JsonRpcErrorResolver());
		jsonServiceExporter.setInterceptorList(List.of(new EpsClientAsRequestAttributeInterceptor()));

		return jsonServiceExporter;
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

	/**
	 * @author Jens Kutzsche
	 */
	private final class EpsClientAsRequestAttributeInterceptor implements JsonRpcInterceptor {

		@Override
		public void preHandleJson(JsonNode json) {

			var clientName = json.findPath("params").findPath("_client").findPath("name");
			if (clientName.isTextual()) {
				request.setAttribute(EPS_CLIENT_ATTRIBUTE, clientName.asText(""));
			}
		}

		@Override
		public void preHandle(Object target, Method method, List<JsonNode> params) {
			// not used
		}

		@Override
		public void postHandle(Object target, Method method, List<JsonNode> params, JsonNode result) {
			// not used
		}

		@Override
		public void postHandleJson(JsonNode json) {
			// not used
		}
	}
}
