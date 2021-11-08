package iris.backend_service.locations.jsonrpc;

import iris.backend_service.jsonrpc.JsonRpcClientDto;
import iris.backend_service.locations.dto.LocationInformation;
import iris.backend_service.locations.dto.LocationOverviewDto;
import iris.backend_service.locations.dto.LocationQueryResult;

import java.util.List;

import javax.validation.Valid;

import com.googlecode.jsonrpc4j.JsonRpcParam;

/**
 * JSON RPC controller for adding/deleting/editing/searching location data in the location service
 */
public interface LocationRPC {

	/**
	 * adds a list of locations to the location service
	 *
	 * @param locationList list of locations
	 * @return status response string
	 */
	String postLocationsToSearchIndex(
			@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client,
			@JsonRpcParam(value = "locations") List<LocationInformation> locationList);

	/**
	 * returns all locations from a given provider
	 *
	 * @return list of Locationreferences
	 */
	List<LocationOverviewDto> getProviderLocations(@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client);

	/**
	 * deletes a specified location
	 *
	 * @param locationId the location ID
	 * @return a response string
	 */
	String deleteLocationFromSearchIndex(
			@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client,
			@JsonRpcParam(value = "locationId") String locationId);

	/**
	 * searches a location with a given keyword
	 *
	 * @param searchKeyword the keyword
	 * @return a locationlist object containing all matching locations
	 */
	LocationQueryResult searchForLocation(
			@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client,
			@JsonRpcParam(value = "searchKeyword") String searchKeyword,
			@JsonRpcParam(value = "pageable") PageableDto pageable);

	/**
	 * returns an object containing all data about a given location
	 *
	 * @param providerId the provider ID
	 * @param locationId the location ID
	 * @return a LocationInformation object
	 */
	Object getLocationDetails(
			@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client,
			@JsonRpcParam(value = "providerId") String providerId,
			@JsonRpcParam(value = "locationId") String locationId);
}
