package iris.location_service.jsonrpc;

import static iris.location_service.utils.LoggingHelper.*;

import iris.location_service.dto.LocationInformation;
import iris.location_service.dto.LocationOverviewDto;
import iris.location_service.dto.LocationQueryResult;
import iris.location_service.service.LocationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;

@AutoJsonRpcServiceImpl
@AllArgsConstructor
@Service
@Slf4j
public class LocationRPCImpl implements LocationRPC {

	private final @NotNull LocationService locationService;
	private final @NotNull HealthEndpoint healthEndpoint;

	@Override
	public String postLocationsToSearchIndex(JsonRpcClientDto client, List<LocationInformation> locationList) {

		var listOfInvalidAddresses = locationService.addLocations(client.getName(), locationList);

		var ret = "OK";
		var logRes = ret;

		if (!listOfInvalidAddresses.isEmpty()) {

			ret = listOfInvalidAddresses.stream()
					.collect(Collectors.joining(", ", "Invalid Locations detected: ", ""));
			logRes = "Invalid Locations detected";
		}

		log.debug("JSON-RPC - Post locations for client: {} (locations: {}) => result: {}", client.getName(),
				locationList.size(), logRes);

		return ret;
	}

	@Override
	public List<LocationOverviewDto> getProviderLocations(JsonRpcClientDto client) {

		var providerLocations = locationService.getProviderLocations(client.getName());

		log.debug("JSON-RPC - Get provider locations for client: {} => returns {} locations", client.getName(),
				providerLocations.size());

		return providerLocations;
	}

	@Override
	public String deleteLocationFromSearchIndex(JsonRpcClientDto client, String locationId) {

		var ret = "NOT FOUND";

		if (locationService.deleteLocation(client.getName(), locationId))
			ret = "OK";

		log.debug("JSON-RPC - Delete location for client: {}; locationId: {} => result: {}", client.getName(),
				obfuscate(locationId), ret);

		return ret;
	}

	@Override
	public LocationQueryResult searchForLocation(JsonRpcClientDto client, String searchKeyword, PageableDto dto) {

		var pageable = PageRequest.of(dto.getPage(), dto.getSize(),
				dto.getSortBy() != null
						? Sort.by(dto.getDirection(), dto.getSortBy())
						: Sort.unsorted());

		Page<LocationInformation> page = locationService.search(searchKeyword, pageable);

		log.debug("JSON-RPC - Search location for client: {} => returns page {} with {} locations; total: {}",
				client.getName(), page.getNumber(), page.getSize(), page.getTotalElements());

		return LocationQueryResult.builder()
				.size(page.getSize())
				.page(page.getNumber())
				.locations(page.getContent())
				.totalElements(page.getTotalElements())
				.build();
	}

	@Override
	public Object getLocationDetails(JsonRpcClientDto client, String providerId, String locationId) {

		var locationInformation = locationService.getLocationByProviderIdAndLocationId(providerId, locationId);

		log.debug("JSON-RPC - Get location details for client: {}; providerId: {}, locationId: {} => found: {}",
				client.getName(), obfuscate(providerId), obfuscate(locationId), locationInformation.isPresent());

		if (locationInformation.isPresent())
			return locationInformation;

		return "NOT FOUND";
	}

	@Override
	public Status getHealth(JsonRpcClientDto client) {

		var status = healthEndpoint.health().getStatus();

		log.debug("JSON-RPC - Get health status for client: {} => result: {}", client.getName(), status);

		return status;
	}
}
