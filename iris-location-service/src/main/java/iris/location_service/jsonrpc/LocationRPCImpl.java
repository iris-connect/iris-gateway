package iris.location_service.jsonrpc;

import static iris.location_service.utils.LoggingHelper.obfuscate;

import iris.location_service.dto.LocationContext;
import iris.location_service.dto.LocationInformation;
import iris.location_service.dto.LocationOverviewDto;
import iris.location_service.dto.LocationQueryResult;
import iris.location_service.service.LocationService;
import iris.location_service.utils.ErrorMessageHelper;
import iris.location_service.utils.ValidationHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;

@AutoJsonRpcServiceImpl
@AllArgsConstructor
@Service
@Slf4j
public class LocationRPCImpl implements LocationRPC {

	private final @NotNull LocationService locationService;
	private final @NotNull HealthEndpoint healthEndpoint;

	@Override
	public String postLocationsToSearchIndex(JsonRpcClientDto client, List<LocationInformation> locationList) {

		if (!isJsonRpcClientDtoInputValid(client)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE);
		}

		List<LocationInformation> locationListValidated = locationInformationInputValidated(locationList);

		var listOfInvalidAddresses = locationService.addLocations(client.getName(), locationListValidated);

		var ret = "OK";
		var logRes = ret;

		if (!listOfInvalidAddresses.isEmpty()) {

			ret = listOfInvalidAddresses.stream().collect(Collectors.joining(", ", "Invalid Locations detected: ", ""));
			logRes = "Invalid Locations detected";
		}

		log.debug("JSON-RPC - Post locations for client: {} (locations: {}) => result: {}", client.getName(), locationList.size(), logRes);

		return ret;
	}

	@Override
	public List<LocationOverviewDto> getProviderLocations(JsonRpcClientDto client) {

		if (client == null || client.getName() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE);
		}

		if (client.getName() != null && !ValidationHelper.isNotShowingSignsForAttacks(client.getName())) {
			log.warn(ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - name: " + client.getName());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE);
		}

		var providerLocations = locationService.getProviderLocations(client.getName());

		log.debug("JSON-RPC - Get provider locations for client: {} => returns {} locations", client.getName(), providerLocations.size());

		return providerLocations;
	}

	@Override
	public String deleteLocationFromSearchIndex(JsonRpcClientDto client, String locationId) {
		boolean isInvalid = false;

		if (client == null || client.getName() == null || locationId == null) {
			isInvalid = true;
		}

		if (client != null && client.getName() != null && !ValidationHelper.isNotShowingSignsForAttacks(client.getName())) {
			log.warn(ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - name: " + client.getName());
			isInvalid = true;
		}

		if (!ValidationHelper.isNotShowingSignsForAttacks(locationId)) {
			log.warn(ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - locationId: " + locationId);
			isInvalid = true;
		}

		if (isInvalid) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE);
		}

		var ret = "NOT FOUND";

		if (locationService.deleteLocation(client.getName(), locationId))
			ret = "OK";

		log.debug("JSON-RPC - Delete location for client: {}; locationId: {} => result: {}", client.getName(), obfuscate(locationId), ret);

		return ret;
	}

	@Override
	public LocationQueryResult searchForLocation(JsonRpcClientDto client, String searchKeyword, PageableDto dto) {

		if (searchKeyword != null && !ValidationHelper.isNotShowingSignsForAttacks(searchKeyword)) {
			log.warn(ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - searchKeyword: " + searchKeyword);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE);
		}

		var pageable =
			PageRequest.of(dto.getPage(), dto.getSize(), dto.getSortBy() != null ? Sort.by(dto.getDirection(), dto.getSortBy()) : Sort.unsorted());

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

		boolean isInvalid = false;

		if (locationId == null || providerId == null) {
			isInvalid = true;
		}

		if (providerId != null && !ValidationHelper.isNotShowingSignsForAttacks(providerId)) {
			log.warn(ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - providerId: " + providerId);
			isInvalid = true;
		}

		if (locationId != null && !ValidationHelper.isNotShowingSignsForAttacks(locationId)) {
			log.warn(ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - locationId: " + locationId);
			isInvalid = true;
		}

		if (isInvalid) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE);
		}

		var locationInformation = locationService.getLocationByProviderIdAndLocationId(providerId, locationId);

		log.debug(
			"JSON-RPC - Get location details for client: {}; providerId: {}, locationId: {} => found: {}",
			client.getName(),
			obfuscate(providerId),
			obfuscate(locationId),
			locationInformation.isPresent());

		if (locationInformation.isPresent())
			return locationInformation;

		return "NOT FOUND";
	}

	@Override
	public Status getHealth(JsonRpcClientDto client) {

		var status = healthEndpoint.health().getStatus();

		log.debug("JSON-RPC - Get health status for client: {} => result: {}", client.getName(), status);

		return status;
	}

	private boolean isJsonRpcClientDtoInputValid(JsonRpcClientDto client) {
		if (client == null || client.getName() == null) {
			return false;
		}

		if (client != null && client.getName() != null && !ValidationHelper.isNotShowingSignsForAttacks(client.getName())) {
			log.warn(ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - providerId: " + client.getName());
			return false;
		}

		return true;
	}

	private List<LocationInformation> locationInformationInputValidated(List<LocationInformation> locationList) {

		List<LocationInformation> validatedLocationList = new ArrayList<LocationInformation>();

		for (LocationInformation locationInformation : locationList) {
			if (locationInformation.getId() != null) {
				if (ValidationHelper.isNotShowingSignsForAttacks(locationInformation.getId())) {
					log.warn(ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - id: " + locationInformation.getId());
					locationInformation.setId(ErrorMessageHelper.INVALID_INPUT_STRING);
				}
			}

			if (locationInformation.getProviderId() != null) {
				if (ValidationHelper.isNotShowingSignsForAttacks(locationInformation.getProviderId())) {
					log.warn(ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - providerId: " + locationInformation.getProviderId());
					locationInformation.setProviderId(ErrorMessageHelper.INVALID_INPUT_STRING);
				}
			}

			if (locationInformation.getName() != null) {
				if (ValidationHelper.isNotShowingSignsForAttacks(locationInformation.getName())) {
					log.warn(ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - name: " + locationInformation.getName());
					locationInformation.setName(ErrorMessageHelper.INVALID_INPUT_STRING);
				}
			}

			if (locationInformation.getPublicKey() != null) {
				if (ValidationHelper.isNotShowingSignsForAttacks(locationInformation.getPublicKey())) {
					log.warn(ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - publicKey: " + locationInformation.getPublicKey());
					locationInformation.setPublicKey(ErrorMessageHelper.INVALID_INPUT_STRING);
				}
			}

			if (locationInformation.getContact() != null) {
				if (locationInformation.getContact().getOfficialName() != null) {
					if (ValidationHelper.isNotShowingSignsForAttacks(locationInformation.getContact().getOfficialName())) {
						log.warn(
							ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - officialName: "
								+ locationInformation.getContact().getOfficialName());
						locationInformation.getContact().setOfficialName(ErrorMessageHelper.INVALID_INPUT_STRING);
					}
				}

				if (locationInformation.getContact().getRepresentative() != null) {
					if (ValidationHelper.isNotShowingSignsForAttacks(locationInformation.getContact().getRepresentative())) {
						log.warn(
							ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - representative: "
								+ locationInformation.getContact().getRepresentative());
						locationInformation.getContact().setRepresentative(ErrorMessageHelper.INVALID_INPUT_STRING);
					}
				}

				if (locationInformation.getContact().getOwnerEmail() != null) {
					if (ValidationHelper.isNotShowingSignsForAttacks(locationInformation.getContact().getOwnerEmail())) {
						log.warn(
							ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - ownerEmail: "
								+ locationInformation.getContact().getOwnerEmail());
						locationInformation.getContact().setOwnerEmail(ErrorMessageHelper.INVALID_INPUT_STRING);
					}
				}

				if (locationInformation.getContact().getEmail() != null) {
					if (ValidationHelper.isNotShowingSignsForAttacks(locationInformation.getContact().getEmail())) {
						log.warn(ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - email: " + locationInformation.getContact().getEmail());
						locationInformation.getContact().setEmail(ErrorMessageHelper.INVALID_INPUT_STRING);
					}
				}

				if (locationInformation.getContact().getPhone() != null) {
					if (ValidationHelper.isNotShowingSignsForAttacks(locationInformation.getContact().getPhone())) {
						log.warn(ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - phone: " + locationInformation.getContact().getPhone());
						locationInformation.getContact().setPhone(ErrorMessageHelper.INVALID_INPUT_STRING);
					}
				}

				if (locationInformation.getContact().getAddress() != null) {
					if (locationInformation.getContact().getAddress().getCity() != null) {
						if (ValidationHelper.isNotShowingSignsForAttacks(locationInformation.getContact().getAddress().getCity())) {
							log.warn(
								ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - city: "
									+ locationInformation.getContact().getAddress().getCity());
							locationInformation.getContact().getAddress().setCity(ErrorMessageHelper.INVALID_INPUT_STRING);
						}
					}

					if (locationInformation.getContact().getAddress().getStreet() != null) {
						if (ValidationHelper.isNotShowingSignsForAttacks(locationInformation.getContact().getAddress().getStreet())) {
							log.warn(
								ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - street: "
									+ locationInformation.getContact().getAddress().getStreet());
							locationInformation.getContact().getAddress().setStreet(ErrorMessageHelper.INVALID_INPUT_STRING);
						}
					}

					if (locationInformation.getContact().getAddress().getZip() != null) {
						if (ValidationHelper.isNotShowingSignsForAttacks(locationInformation.getContact().getAddress().getZip())) {
							log.warn(
								ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - zip: "
									+ locationInformation.getContact().getAddress().getZip());
							locationInformation.getContact().getAddress().setZip(ErrorMessageHelper.INVALID_INPUT_STRING);
						}
					}
				}
			}

			if (locationInformation.getContexts() != null) {
				List<LocationContext> contextsValidated = new ArrayList<LocationContext>();

				for (LocationContext locationContext : locationInformation.getContexts()) {
					if (locationContext.getId() != null) {
						if (ValidationHelper.isNotShowingSignsForAttacks(locationContext.getId())) {
							log.warn(ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - id: " + locationContext.getId());
							locationContext.setId(ErrorMessageHelper.INVALID_INPUT_STRING);
						}
					}

					if (locationContext.getName() != null) {
						if (ValidationHelper.isNotShowingSignsForAttacks(locationContext.getName())) {
							log.warn(ErrorMessageHelper.INVALID_INPUT_EXCEPTION_MESSAGE + " - name: " + locationContext.getName());
							locationContext.setName(ErrorMessageHelper.INVALID_INPUT_STRING);
						}
					}

					contextsValidated.add(locationContext);
				}

				locationInformation.setContexts(contextsValidated);
			}

			validatedLocationList.add(locationInformation);
		}

		return validatedLocationList;
	}

}
