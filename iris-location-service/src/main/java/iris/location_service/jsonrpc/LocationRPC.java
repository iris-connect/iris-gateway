package iris.location_service.jsonrpc;

import iris.location_service.dto.LocationInformation;
import iris.location_service.dto.LocationOverviewDto;
import iris.location_service.dto.LocationQueryResult;

import java.util.List;

import javax.validation.Valid;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

@JsonRpcService("/location-rpc")
public interface LocationRPC {
	String postLocationsToSearchIndex(
			@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client,
			@JsonRpcParam(value = "locations") List<LocationInformation> locationList);

	List<LocationOverviewDto> getProviderLocations(@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client);

	String deleteLocationFromSearchIndex(
			@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client,
			@JsonRpcParam(value = "locationId") String locationId);

	LocationQueryResult searchForLocation(
			@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client,
			@JsonRpcParam(value = "searchKeyword") String searchKeyword,
			@JsonRpcParam(value = "pageable") PageableDto pageable);

	Object getLocationDetails(
			@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client,
			@JsonRpcParam(value = "providerId") String providerId,
			@JsonRpcParam(value = "locationId") String locationId);
}
