package iris.location_service.jsonrpc;

import iris.location_service.dto.LocationInformation;
import iris.location_service.dto.LocationList;
import iris.location_service.dto.LocationOverviewDto;
import iris.location_service.service.LocationService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Optional;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;

@AutoJsonRpcServiceImpl
@AllArgsConstructor
@Service
public class LocationRPCImpl implements LocationRPC {

	private final @NotNull LocationService locationService;

	public String postLocationsToSearchIndex(JsonRpcClientDto client, List<LocationInformation> locationList) {
		locationService.addLocations(client.getName(), locationList);
		return "OK";
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
	public LocationList searchForLocation(String searchKeyword) {
		return new LocationList(locationService.search(searchKeyword));
	}

	@Override
	public Object getLocationDetails(
			@JsonRpcParam(value = "providerId") String providerId,
			@JsonRpcParam(value = "locationId") String locationId) {
		Optional<LocationInformation> locationInformation = locationService.getLocationByProviderIdAndLocationId(providerId,
				locationId);

		if (locationInformation.isPresent())
			return locationInformation;

		return "NOT FOUND";
	}

}
