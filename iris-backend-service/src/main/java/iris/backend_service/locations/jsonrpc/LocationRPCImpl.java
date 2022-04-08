package iris.backend_service.locations.jsonrpc;

import static iris.backend_service.core.logging.LoggingHelper.*;

import iris.backend_service.core.validation.AttackDetector;
import iris.backend_service.jsonrpc.JsonRpcClientDto;
import iris.backend_service.locations.dto.LocationInformation;
import iris.backend_service.locations.dto.LocationOverviewDto;
import iris.backend_service.locations.dto.LocationQueryResult;
import iris.backend_service.locations.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationRPCImpl implements LocationRPC {

	private final @NotNull LocationService locationService;
	private final @NotNull AttackDetector attackDetector;

	@Override
	public String postLocationsToSearchIndex(JsonRpcClientDto client, List<LocationInformation> locations) {

		var clientName = client.getName();
		validatePostLimit(locations, clientName);

		locationService.addLocations(clientName, locations);

		log.debug("JSON-RPC - Post locations for client: {} (locations: {})", clientName, locations.size());

		return "OK";
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

		if (locationService.deleteLocation(client.getName(), locationId)) {
			ret = "OK";
		}

		log.debug("JSON-RPC - Delete location for client: {}; locationId: {} => result: {}", client.getName(),
				obfuscateEndPart(locationId), ret);

		return ret;
	}

	@Override
	public LocationQueryResult searchForLocation(JsonRpcClientDto client, String searchKeyword, PageableDto dto) {

		var pageable = PageRequest.of(dto.getPage(), dto.getSize(),
				dto.getSortBy() != null ? Sort.by(dto.getDirection(), dto.getSortBy()) : Sort.unsorted());

		Page<LocationInformation> page = locationService.search(searchKeyword, pageable);

		log.debug(
				"JSON-RPC - Search location for client: {} => returns page {} with {} locations; total: {}",
				client.getName(),
				page.getNumber(),
				page.getSize(),
				page.getTotalElements());

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

		log.debug(
				"JSON-RPC - Get location details for client: {}; providerId: {}, locationId: {} => found: {}",
				client.getName(),
				obfuscateEndPart(providerId),
				obfuscateEndPart(locationId),
				locationInformation.isPresent());

		if (locationInformation.isPresent()) {
			return locationInformation;
		}

		return "NOT FOUND";
	}

	private void validatePostLimit(List<LocationInformation> locationList, String client) {
		if (attackDetector.isPostOutOfLimit(locationList, client)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AttackDetector.INVALID_INPUT_EXCEPTION_MESSAGE);
		}
	}
}
