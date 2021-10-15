package iris.backend_service.config;

import iris.backend_service.jsonrpc.HealthRPC;
import iris.backend_service.locations.jsonrpc.LocationRPC;
import iris.backend_service.messages.MessageRPC;
import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.googlecode.jsonrpc4j.spring.CompositeJsonServiceExporter;

@Configuration
@AllArgsConstructor
public class ServiceEndpointsConfig {

	public static final String ENDPOINT = "/backend-rpc";

	private final HealthRPC health;
	private final LocationRPC locations;
	private final MessageRPC alerts;

	@Bean(name = ENDPOINT)
	public CompositeJsonServiceExporter jsonRpcServiceImplExporter() {

		var compositeJsonServiceExporter = new CompositeJsonServiceExporter();
		compositeJsonServiceExporter.setServices(new Object[] { health, locations, alerts });
		compositeJsonServiceExporter
				.setServiceInterfaces(new Class<?>[] { HealthRPC.class, LocationRPC.class, MessageRPC.class });
		compositeJsonServiceExporter.setAllowMultipleInheritance(true);

		return compositeJsonServiceExporter;
	}
}
