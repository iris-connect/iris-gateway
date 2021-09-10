package iris.backend_service.locations.jsonrpc;

import static iris.backend_service.locations.utils.LoggingHelper.*;

import iris.backend_service.jsonrpc.JsonRpcClientDto;
import iris.backend_service.locations.dto.LocationAddress;
import iris.backend_service.locations.dto.LocationContact;
import iris.backend_service.locations.dto.LocationContext;
import iris.backend_service.locations.dto.LocationInformation;
import iris.backend_service.locations.dto.LocationOverviewDto;
import iris.backend_service.locations.dto.LocationQueryResult;
import iris.backend_service.locations.service.LocationService;
import iris.backend_service.locations.utils.ErrorMessageHelper;
import iris.backend_service.locations.utils.ValidationHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
@Slf4j
public class LocationRPCImpl implements LocationRPC {

	private static final String FIELD_SEARCH_KEYWORD = "searchKeyword";
	private static final String FIELD_ZIP = "zip";
	private static final String FIELD_STREET = "street";
	private static final String FIELD_CITY = "city";
	private static final String FIELD_PHONE = "phone";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_OWNER_EMAIL = "ownerEmail";
	private static final String FIELD_REPRESENTATIVE = "representative";
	private static final String FIELD_OFFICIAL_NAME = "officialName";
	private static final String FIELD_PUBLIC_KEY = "publicKey";
	private static final String FIELD_ID = "id";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_LOCATION_ID = "locationId";
	private static final String FIELD_PROVIDER_ID = "providerId";

	private final @NotNull LocationService locationService;
	private final @NotNull ValidationHelper validHelper;

	@Override
	public String postLocationsToSearchIndex(JsonRpcClientDto client, List<LocationInformation> locationList) {

		validateJsonRpcClientDto(client);
		validatePostLimit(locationList, client.getName());

		var locationListValidated = validateLocationInformation(locationList, client.getName());

		var listOfInvalidAddresses = locationService.addLocations(client.getName(), locationListValidated);

		var ret = "OK";
		var logRes = ret;

		if (!listOfInvalidAddresses.isEmpty()) {

			ret = listOfInvalidAddresses.stream().collect(Collectors.joining(", ", "Invalid Locations detected: ", ""));
			logRes = "Invalid Locations detected";
		}

		log.debug("JSON-RPC - Post locations for client: {} (locations: {}) => result: {}", client.getName(),
				locationList.size(), logRes);

		return ret;
	}

	@Override
	public List<LocationOverviewDto> getProviderLocations(JsonRpcClientDto client) {

		validateJsonRpcClientDto(client);

		var providerLocations = locationService.getProviderLocations(client.getName());

		log.debug("JSON-RPC - Get provider locations for client: {} => returns {} locations", client.getName(),
				providerLocations.size());

		return providerLocations;
	}

	@Override
	public String deleteLocationFromSearchIndex(JsonRpcClientDto client, String locationId) {

		validateInputForDeleteLocationFromSearchIndex(client, locationId);

		var ret = "NOT FOUND";

		if (locationService.deleteLocation(client.getName(), locationId))
			ret = "OK";

		log.debug("JSON-RPC - Delete location for client: {}; locationId: {} => result: {}", client.getName(),
				obfuscateEndPart(locationId), ret);

		return ret;
	}

	@Override
	public LocationQueryResult searchForLocation(JsonRpcClientDto client, String searchKeyword, PageableDto dto) {

		if (validHelper.isPossibleAttack(searchKeyword, FIELD_SEARCH_KEYWORD, true, client.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE);
		}

		var pageable = PageRequest.of(dto.getPage(), dto.getSize(),
				dto.getSortBy() != null ? Sort.by(dto.getDirection(), dto.getSortBy()) : Sort.unsorted());

		Page<LocationInformation> page = locationService.search(searchKeyword, pageable);

		log.debug(
				"JSON-RPC - Search location for client: {} => returns page {} with {} locations; total: {}",
				client.getName(),
				page.getNumber(),
				page.getSize(),
				page.getTotalElements());

		return LocationQueryResult.builder()
				.size(page.getSize())
				.page(page.getNumber())
				.locations(page.getContent())
				.totalElements(page.getTotalElements())
				.build();
	}

	@Override
	public Object getLocationDetails(JsonRpcClientDto client, String providerId, String locationId) {

		validateInputForGetLocationDetails(providerId, locationId, client);

		var locationInformation = locationService.getLocationByProviderIdAndLocationId(providerId, locationId);

		log.debug(
				"JSON-RPC - Get location details for client: {}; providerId: {}, locationId: {} => found: {}",
				client.getName(),
				obfuscateEndPart(providerId),
				obfuscateEndPart(locationId),
				locationInformation.isPresent());

		if (locationInformation.isPresent())
			return locationInformation;

		return "NOT FOUND";
	}

	private void validateJsonRpcClientDto(JsonRpcClientDto client) {
		if (client == null
				|| validHelper.isPossibleAttackForRequiredValue(client.getName(), FIELD_NAME, false, client.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE);
		}
	}

	private void validatePostLimit(List<LocationInformation> locationList, String client) {
		if (validHelper.isPostOutOfLimit(locationList, client)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE);
		}
	}

	private void validateInputForDeleteLocationFromSearchIndex(JsonRpcClientDto client, String locationId) {

		validateJsonRpcClientDto(client);

		if (validHelper.isPossibleAttackForRequiredValue(locationId, FIELD_LOCATION_ID, true, client.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE);
		}
	}

	private void validateInputForGetLocationDetails(String providerId, String locationId, JsonRpcClientDto client) {

		if (validHelper.isPossibleAttackForRequiredValue(providerId, FIELD_PROVIDER_ID, true, client.getName())
				|| validHelper.isPossibleAttackForRequiredValue(locationId, FIELD_LOCATION_ID, true, client.getName())) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE);
		}
	}

	private List<LocationInformation> validateLocationInformation(List<LocationInformation> locationList, String client) {

		return locationList.stream()
				.map(it -> validateLocationInformation(it, client))
				.collect(Collectors.toList());
	}

	private LocationInformation validateLocationInformation(LocationInformation locationInformation, String client) {

		if (validHelper.isPossibleAttack(locationInformation.getId(), FIELD_ID, true, client)) {
			locationInformation.setId(ErrorMessageHelper.INVALID_INPUT_STRING);
		}

		if (validHelper.isPossibleAttack(locationInformation.getProviderId(), FIELD_PROVIDER_ID, true, client)) {
			locationInformation.setProviderId(ErrorMessageHelper.INVALID_INPUT_STRING);
		}

		if (validHelper.isPossibleAttack(locationInformation.getName(), FIELD_NAME, true, client)) {
			locationInformation.setName(ErrorMessageHelper.INVALID_INPUT_STRING);
		}

		if (validHelper.isPossibleAttack(locationInformation.getPublicKey(), FIELD_PUBLIC_KEY, true, client)) {
			locationInformation.setPublicKey(ErrorMessageHelper.INVALID_INPUT_STRING);
		}

		if (locationInformation.getContact() != null) {
			validateContact(locationInformation.getContact(), client);
		}

		if (locationInformation.getContexts() != null) {
			var validateContext = validateContext(locationInformation.getContexts(), client);
			locationInformation.setContexts(validateContext);
		}

		return locationInformation;
	}

	private void validateContact(LocationContact contact, String client) {

		if (validHelper.isPossibleAttack(contact.getOfficialName(), FIELD_OFFICIAL_NAME, true, client)) {
			contact.setOfficialName(ErrorMessageHelper.INVALID_INPUT_STRING);
		}

		if (validHelper.isPossibleAttack(contact.getRepresentative(), FIELD_REPRESENTATIVE, true, client)) {
			contact.setRepresentative(ErrorMessageHelper.INVALID_INPUT_STRING);
		}

		if (validHelper.isPossibleAttack(contact.getOwnerEmail(), FIELD_OWNER_EMAIL, true, client)) {
			contact.setOwnerEmail(ErrorMessageHelper.INVALID_INPUT_STRING);
		}

		if (validHelper.isPossibleAttack(contact.getEmail(), FIELD_EMAIL, true, client)) {
			contact.setEmail(ErrorMessageHelper.INVALID_INPUT_STRING);
		}

		if (validHelper.isPossibleAttackForPhone(contact.getPhone(), FIELD_PHONE, true, client)) {
			contact.setPhone(ErrorMessageHelper.INVALID_INPUT_STRING);
		}

		if (contact.getAddress() != null) {
			validateAddress(contact.getAddress(), client);
		}
	}

	private void validateAddress(LocationAddress address, String client) {

		if (validHelper.isPossibleAttack(address.getCity(), FIELD_CITY, true, client)) {
			address.setCity(ErrorMessageHelper.INVALID_INPUT_STRING);
		}

		if (validHelper.isPossibleAttack(address.getStreet(), FIELD_STREET, true, client)) {
			address.setStreet(ErrorMessageHelper.INVALID_INPUT_STRING);
		}

		if (validHelper.isPossibleAttack(address.getZip(), FIELD_ZIP, true, client)) {
			address.setZip(ErrorMessageHelper.INVALID_INPUT_STRING);
		}
	}

	private List<LocationContext> validateContext(List<LocationContext> contexts, String client) {
		var contextsValidated = new ArrayList<LocationContext>();

		for (var locationContext : contexts) {
			if (validHelper.isPossibleAttack(locationContext.getId(), FIELD_ID, true, client)) {
				locationContext.setId(ErrorMessageHelper.INVALID_INPUT_STRING);
			}

			if (validHelper.isPossibleAttack(locationContext.getName(), FIELD_NAME, true, client)) {
				locationContext.setName(ErrorMessageHelper.INVALID_INPUT_STRING);
			}

			contextsValidated.add(locationContext);
		}

		return contextsValidated;
	}
}
