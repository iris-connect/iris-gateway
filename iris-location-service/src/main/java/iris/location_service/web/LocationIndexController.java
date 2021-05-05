package iris.location_service.web;
/*
 * Controller of the location index. Handles adding/removing entries from the index as well as search
 *
 * @author Kai Greshake
 */

import iris.location_service.dto.LocationInformation;
import iris.location_service.dto.LocationList;
import iris.location_service.search.db.DBSearchIndex;
import iris.location_service.search.db.LocationRepository;
import iris.location_service.search.db.model.Location;
import iris.location_service.search.db.model.LocationIdentifier;
import iris.location_service.service.LocationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

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
	public ResponseEntity<Void> deleteLocationFromSearchIndex(@RequestHeader(value = "x-provider-id", required = true) UUID providerId, @PathVariable("id") String id) {
		// TODO: Authenticate API Access

        if (locationService.deleteLocation(providerId, id))
            return new ResponseEntity<Void>(HttpStatus.OK);

        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/search-index/locations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> postLocationsToSearchIndex(@RequestHeader(value = "x-provider-id", required = true) UUID providerId, @Valid @RequestBody LocationList body) {
        locationService.addLocations(providerId, body.getLocations());
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

	@GetMapping("/search/{search_keyword}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<LocationList> searchSearchKeywordGet(
			@Size(min = 4) @PathVariable("search_keyword") String searchKeyword) {
		// TODO: Authenticate API Access

		return new ResponseEntity<LocationList>(new LocationList(index.search(searchKeyword)), HttpStatus.OK);
	}

	@GetMapping("/search/{providerId}/{locationId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<LocationInformation> getLocation(@PathVariable("providerId") String providerId,
			@PathVariable("locationId") String locationId) {
		// TODO: Authenticate API Access

		var ident = new LocationIdentifier(providerId, locationId);

		return locationRepository.findById(ident)
				.map(this::toDto)
				.map(ResponseEntity::ok)
				.orElseGet(ResponseEntity.notFound()::build);
	}

	private LocationInformation toDto(Location it) {

		var location = mapper.map(it, LocationInformation.class);

		location.setId(it.getId().getLocationId());
		location.setProviderId(it.getId().getProviderId());

		return location;
	}
}
