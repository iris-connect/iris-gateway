package iris.location_service.jsonrpc;

import iris.location_service.dto.LocationInformation;
import iris.location_service.dto.LocationOverviewDto;
import iris.location_service.dto.LocationQueryResult;
import iris.location_service.service.LocationService;
import lombok.AllArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
public class LocationRPCImpl implements LocationRPC {

	private final @NotNull LocationService locationService;
	private final @NotNull HealthEndpoint healthEndpoint;

	@Override
	public String postLocationsToSearchIndex(JsonRpcClientDto client, List<LocationInformation> locationList) {
		List<String> listOfInvalidAddresses = locationService.addLocations(client.getName(), locationList);

		if (!listOfInvalidAddresses.isEmpty()) {
			String output = "Invalid Locations detected: ";
			for (Iterator iterator = listOfInvalidAddresses.iterator(); iterator.hasNext();) {
				output += (String) iterator.next() + ", ";
			}

			return output.substring(0, output.length() - 2);
		} else {
			return "OK";
		}
	}

	@Override
	public List<LocationOverviewDto> getProviderLocations(JsonRpcClientDto client) {
		return locationService.getProviderLocations(client.getName());
	}

	@Override
	public String deleteLocationFromSearchIndex(JsonRpcClientDto client, String locationId) {
		if (locationService.deleteLocation(client.getName(), locationId))
			return "OK";
		return "NOT FOUND";
	}

	@Override
	public LocationQueryResult searchForLocation(JsonRpcClientDto client, String searchKeyword, PageableDto dto) {

		var pageable = PageRequest.of(dto.getPage(), dto.getSize());

		if (dto.getSortBy() != null) {
			pageable = PageRequest.of(dto.getPage(), dto.getSize(), Sort.by(dto.getDirection(), dto.getSortBy()));
		}

		Page<LocationInformation> page = locationService.search(searchKeyword, pageable);

		return LocationQueryResult.builder()
			.size(page.getSize())
			.page(page.getNumber())
			.locations(page.getContent())
			.totalElements(page.getTotalElements())
			.build();
	}

	@Override
	public Object getLocationDetails(JsonRpcClientDto client, String providerId, String locationId) {
		Optional<LocationInformation> locationInformation = locationService.getLocationByProviderIdAndLocationId(providerId, locationId);

		if (locationInformation.isPresent())
			return locationInformation;

		return "NOT FOUND";
	}

	@Override
	public Status getHealth(JsonRpcClientDto client) {
		return healthEndpoint.health().getStatus();
	}
}
