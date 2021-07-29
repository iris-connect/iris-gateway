package iris.backend_service.locations.jsonrpc;

import iris.backend_service.jsonrpc.JsonRpcClientDto;
import iris.backend_service.locations.dto.LocationInformation;
import iris.backend_service.locations.dto.LocationOverviewDto;
import iris.backend_service.locations.dto.LocationQueryResult;

import java.util.List;

import javax.validation.Valid;

import com.googlecode.jsonrpc4j.JsonRpcParam;

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
