package iris.backend_service.locations.jsonrpc;

import iris.backend_service.core.validation.NoSignOfAttack;
import iris.backend_service.jsonrpc.JsonRpcClientDto;
import iris.backend_service.locations.dto.LocationInformation;
import iris.backend_service.locations.dto.LocationOverviewDto;
import iris.backend_service.locations.dto.LocationQueryResult;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.googlecode.jsonrpc4j.JsonRpcParam;

/**
 * JSON RPC controller for adding/deleting/editing/searching location data in the location service
 */
@Validated
public interface LocationRPC {

	/**
	 * adds a list of locations to the location service
	 *
	 * @param locations list of locations
	 * @return status response string
	 */
	String postLocationsToSearchIndex(
			@JsonRpcParam(value = "_client") @NotNull @Valid JsonRpcClientDto client,
			@JsonRpcParam(value = "locations") @Valid List<LocationInformation> locations);

	/**
	 * returns all locations from a given provider
	 *
	 * @return list of Locationreferences
	 */
	List<LocationOverviewDto> getProviderLocations(
			@JsonRpcParam(value = "_client") @NotNull @Valid JsonRpcClientDto client);

	/**
	 * deletes a specified location
	 *
	 * @param locationId the location ID
	 * @return a response string
	 */
	String deleteLocationFromSearchIndex(
			@JsonRpcParam(value = "_client") @NotNull @Valid JsonRpcClientDto client,
			@JsonRpcParam(value = "locationId") @NotBlank @NoSignOfAttack String locationId);

	/**
	 * searches a location with a given keyword
	 *
	 * @param searchKeyword the keyword
	 * @return a locationlist object containing all matching locations
	 */
	LocationQueryResult searchForLocation(
			@JsonRpcParam(value = "_client") @NotNull @Valid JsonRpcClientDto client,
			@JsonRpcParam(value = "searchKeyword") @NoSignOfAttack String searchKeyword,
			@JsonRpcParam(value = "pageable") PageableDto pageable);

	/**
	 * returns an object containing all data about a given location
	 *
	 * @param providerId the provider ID
	 * @param locationId the location ID
	 * @return a LocationInformation object
	 */
	Object getLocationDetails(
			@JsonRpcParam(value = "_client") @NotNull @Valid JsonRpcClientDto client,
			@JsonRpcParam(value = "providerId") @NotBlank @NoSignOfAttack String providerId,
			@JsonRpcParam(value = "locationId") @NotBlank @NoSignOfAttack String locationId);
}
