package iris.location_service.jsonrpc;

import iris.location_service.dto.LocationInformation;
import iris.location_service.dto.LocationList;
import iris.location_service.dto.LocationOverviewDto;

import java.util.List;
import java.util.UUID;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

/**
 * json RPC controller for adding/deleting/editing/searching location data in the location service
 */
@JsonRpcService("/location-rpc")
public interface LocationRPC {

	/**
	 * adds a list of locations to the location service
	 * @param providerId provider ID coming from the requesting application
	 * @param locationList list of locations
	 * @return status response string
	 */
	String postLocationsToSearchIndex(
			@JsonRpcParam(value = "providerId") UUID providerId,
			@JsonRpcParam(value = "locations") List<LocationInformation> locationList);

	/**
	 * returns all locations from a given provider
	 * @param providerId the provider ID
	 * @return list of Locationreferences
	 */
	List<LocationOverviewDto> getProviderLocations(@JsonRpcParam(value = "providerId") UUID providerId);

	/**
	 * deletes a specified location
	 * @param providerId the provider ID
	 * @param locationId the location ID
	 * @return a response string
	 */
	String deleteLocationFromSearchIndex(
			@JsonRpcParam(value = "providerId") UUID providerId,
			@JsonRpcParam(value = "locationId") String locationId);

	/**
	 * searches a location with a given keyword
	 * @param searchKeyword the keyword
	 * @return a locationlist object containing all matching locations
	 */
	LocationList searchForLocation(@JsonRpcParam(value = "searchKeyword") String searchKeyword);

	/**
	 * returns an object containing all data about a given location
	 * @param providerId the provider ID
	 * @param locationId the location ID
	 * @return a LocationInformation object
	 */
	Object getLocationDetails(
			@JsonRpcParam(value = "providerId") String providerId,
			@JsonRpcParam(value = "locationId") String locationId);
}
