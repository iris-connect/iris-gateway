package iris.location_service.jsonrpc;

import iris.location_service.dto.LocationInformation;
import iris.location_service.dto.LocationList;
import iris.location_service.dto.LocationOverviewDto;
import iris.location_service.service.LocationService;
import lombok.AllArgsConstructor;

import java.util.List;
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

	public String postLocationsToSearchIndex(String providerId, List<LocationInformation> locationList) {
		locationService.addLocations(providerId, locationList);
		return "OK";
	}

	@Override
	public List<LocationOverviewDto> getProviderLocations(String providerId) {
		return locationService.getProviderLocations(providerId);
	}

	@Override
	public String deleteLocationFromSearchIndex(String providerId, String locationId) {
		if (locationService.deleteLocation(providerId, locationId))
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
