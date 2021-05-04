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
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@AllArgsConstructor
public class LocationIndexController {

	/*
	TODO: This placeholder is for information we will get from authenticating the API requests-
	i.e. which provider sent the request. Mocked to be constant 0 for now.
	 */

	private ModelMapper mapper;

	private LocationRepository locationRepository;

	private DBSearchIndex index;

	@DeleteMapping("/search-index/locations/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<Void> deleteLocationFromSearchIndex(@RequestHeader(value = "x-provider-id", required = true) UUID providerId, @PathVariable("id") String id) {
		// TODO: Authenticate API Access

		// Construct a new ID to match the (provider, id) pair key
		LocationIdentifier ident = new LocationIdentifier(providerId.toString(), id);

		Optional<Location> match = locationRepository.findById(ident);
		if (match.isPresent()) {
			locationRepository.delete(match.get());
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/search-index/locations")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> postLocationsToSearchIndex(@RequestHeader(value = "x-provider-id", required = true) UUID providerId, @Valid @RequestBody LocationList body) {
		// TODO: Authenticate API Access

		// TODO: Define sensible limits for this API

        var data = body.getLocations().stream().map(entry -> {

            entry.setProviderId(providerId.toString()); // Reset possibly sent provider id. We need to ensure this comes from the
            // authentication system and isn't user-provided!
            var location = mapper.map(entry, Location.class);
            // For the search index, we are only interested in a subset of the data structure for location information
            // Can be replaced
            location.setId(new LocationIdentifier(providerId.toString(), entry.getId()));
            return location;
        }).collect(Collectors.toList());

        locationRepository.saveAll(data);
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
