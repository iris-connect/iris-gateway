package de.healthIMIS.iris.irislocationservice.web;
/*
 * Controller of the location index. Handles adding/removing entries from the index as well as search
 *
 * @author Kai Greshake
 */

import de.healthIMIS.iris.irislocationservice.dto.LocationInformation;
import de.healthIMIS.iris.irislocationservice.dto.LocationList;
import de.healthIMIS.iris.irislocationservice.search.db.DBSearchIndex;
import de.healthIMIS.iris.irislocationservice.search.db.LocationRepository;
import de.healthIMIS.iris.irislocationservice.search.db.model.Location;
import de.healthIMIS.iris.irislocationservice.search.db.model.LocationIdentifier;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LocationIndexController {

	/*
	TODO: This placeholder is for information we will get from authenticating the API requests-
	i.e. which provider sent the request. Mocked to be constant 0 for now.
	 */
	private final static String temporary_provider_id = "00000-00000-00000-00000";

	private ModelMapper mapper;

	private LocationRepository locationRepository;

	private DBSearchIndex index;

	@DeleteMapping("/search-index/locations/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<Void> deleteLocationFromSearchIndex(@PathVariable("id") String id) {
		// TODO: Authenticate API Access

		// Construct a new ID to match the (provider, id) pair key
		LocationIdentifier ident = new LocationIdentifier(temporary_provider_id, id);

		Optional<Location> match = locationRepository.findById(ident);
		if (match.isPresent()) {
			locationRepository.delete(match.get());
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/search-index/locations")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> postLocationsToSearchIndex(@Valid @RequestBody LocationList body) {
		// TODO: Authenticate API Access

		// TODO: Define sensible limits for this API

		var locations = body.getLocations();
		if (locations != null) {
			var data = body.getLocations().stream().map(entry -> {
				entry.setProvider_id(""); // Reset possibly sent provider id. We need to ensure this comes from the
				// authentication system and isn't user-provided!
				var location = mapper.map(entry, Location.class);
				// For the search index, we are only interested in a subset of the data structure for location information
				// Can be replaced
				location.setId(new LocationIdentifier(temporary_provider_id, entry.getId()));
				return location;
			}).collect(Collectors.toList());

			locationRepository.saveAll(data);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@GetMapping("/search/{search_keyword}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<LocationList> searchSearchKeywordGet(
			@DecimalMin("4") @PathVariable("search_keyword") String searchKeyword) {
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
		location.setProvider_id(it.getId().getProviderId());

		return location;
	}
}
