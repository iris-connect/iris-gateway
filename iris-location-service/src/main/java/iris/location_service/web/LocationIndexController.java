package iris.location_service.web;
/*
 * Controller of the location index. Handles adding/removing entries from the index as well as search
 *
 * @author Kai Greshake
 */

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import iris.location_service.dto.LocationInformation;
import iris.location_service.dto.LocationList;
import iris.location_service.search.db.DBSearchIndex;
import iris.location_service.search.db.LocationRepository;
import iris.location_service.service.LocationService;
import lombok.AllArgsConstructor;

@RestController
@Validated
@AllArgsConstructor
public class LocationIndexController {

	/*
	TODO: This placeholder is for information we will get from authenticating the API requests-
	i.e. which provider sent the request. Mocked to be constant 0 for now.
	 */

	private final @NotNull LocationService locationService;
	private final ModelMapper mapper;
	private final LocationRepository locationRepository;
	private final DBSearchIndex index;

	@DeleteMapping("/search-index/locations/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<Void> deleteLocationFromSearchIndex(
			@RequestHeader(value = "x-provider-id", required = true) String providerId, @PathVariable("id") String id) {
		// TODO: Authenticate API Access

		if (locationService.deleteLocation(providerId, id))
			return new ResponseEntity<Void>(HttpStatus.OK);

		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/search-index/locations")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> postLocationsToSearchIndex(
			@RequestHeader(value = "x-provider-id", required = true) String providerId,
			@Valid @RequestBody LocationList body) {
		locationService.addLocations(providerId, body.getLocations());
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/search/{search_keyword}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<LocationList> searchSearchKeywordGet(
			@Size(min = 4) @PathVariable("search_keyword") String searchKeyword, Pageable pageable) {
		// TODO: Authenticate API Access

		Page<LocationInformation> page = index.search(searchKeyword, pageable);
		LocationList locationList = LocationList.builder().locations(page.getContent())
				.totalElements(page.getTotalElements()).build();
		return new ResponseEntity<>(locationList, HttpStatus.OK);
	}

	@GetMapping("/search/{providerId}/{locationId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<LocationInformation> getLocation(@PathVariable("providerId") String providerId,
			@PathVariable("locationId") String locationId) {
		// TODO: Authenticate API Access
		Optional<LocationInformation> locationInformation = locationService.getLocationByProviderIdAndLocationId(providerId,
				locationId);

		return locationInformation.map(information -> new ResponseEntity<>(
				information, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

	}

}
