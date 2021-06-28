package iris.location_service.jsonrpc;

import iris.location_service.dto.LocationInformation;
import iris.location_service.dto.LocationOverviewDto;
import iris.location_service.dto.LocationQueryResult;

import java.util.List;

import javax.validation.Valid;

import org.springframework.boot.actuate.health.Status;

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
			@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client,
			@JsonRpcParam(value = "locations") List<LocationInformation> locationList);

	/**
	 * returns all locations from a given provider
	 * @param providerId the provider ID
	 * @return list of Locationreferences
	 */
	List<LocationOverviewDto> getProviderLocations(@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client);

	/**
	 * deletes a specified location
	 * @param providerId the provider ID
	 * @param locationId the location ID
	 * @return a response string
	 */
	String deleteLocationFromSearchIndex(
			@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client,
			@JsonRpcParam(value = "locationId") String locationId);

	/**
	 * searches a location with a given keyword
	 * @param searchKeyword the keyword
	 * @return a locationlist object containing all matching locations
	 */
	LocationQueryResult searchForLocation(
			@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client,
			@JsonRpcParam(value = "searchKeyword") String searchKeyword,
			@JsonRpcParam(value = "pageable") PageableDto pageable);

	/**
	 * returns an object containing all data about a given location
	 * @param providerId the provider ID
	 * @param locationId the location ID
	 * @return a LocationInformation object
	 */
	Object getLocationDetails(
			@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client,
			@JsonRpcParam(value = "providerId") String providerId,
			@JsonRpcParam(value = "locationId") String locationId);

	Status getHealth(@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client);
}
