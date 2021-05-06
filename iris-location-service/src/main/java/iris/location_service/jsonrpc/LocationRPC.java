package iris.location_service.jsonrpc;

import iris.location_service.dto.LocationInformation;
import iris.location_service.dto.LocationList;
import iris.location_service.dto.LocationOverviewDto;

import java.util.List;
import java.util.UUID;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

@JsonRpcService("/location-rpc")
public interface LocationRPC {
	String postLocationsToSearchIndex(
			@JsonRpcParam(value = "providerId") UUID providerId,
			@JsonRpcParam(value = "locations") List<LocationInformation> locationList);

	List<LocationOverviewDto> getProviderLocations(@JsonRpcParam(value = "providerId") UUID providerId);

	String deleteLocationFromSearchIndex(
			@JsonRpcParam(value = "providerId") UUID providerId,
			@JsonRpcParam(value = "locationId") String locationId);

	LocationList searchForLocation(@JsonRpcParam(value = "searchKeyword") String searchKeyword);

	Object getLocationDetails(
			@JsonRpcParam(value = "providerId") String providerId,
			@JsonRpcParam(value = "locationId") String locationId);
}
