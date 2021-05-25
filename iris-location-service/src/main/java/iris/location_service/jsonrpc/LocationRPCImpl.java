package iris.location_service.jsonrpc;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;

import iris.location_service.dto.LocationInformation;
import iris.location_service.dto.LocationList;
import iris.location_service.dto.LocationOverviewDto;
import iris.location_service.service.LocationService;
import lombok.AllArgsConstructor;

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
	public LocationList searchForLocation(JsonRpcClientDto client, String searchKeyword, PageableDto pageable) {
		Pageable p = pageable.getSortBy() != null
				? PageRequest.of(pageable.getPage(), pageable.getSize(),
						Sort.by(pageable.getDirection(), pageable.getSortBy()))
				: PageRequest.of(pageable.getPage(), pageable.getSize());
		Page<LocationInformation> page = locationService.search(searchKeyword, p);
		return LocationList.builder().locations(page.getContent()).totalElements(page.getTotalElements()).build();
	}

	@Override
	public Object getLocationDetails(
			JsonRpcClientDto client,
			String providerId,
			String locationId) {
		Optional<LocationInformation> locationInformation = locationService.getLocationByProviderIdAndLocationId(providerId,
				locationId);

		if (locationInformation.isPresent())
			return locationInformation;

		return "NOT FOUND";
	}

}
