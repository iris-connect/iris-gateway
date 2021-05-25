package iris.location_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import iris.location_service.dto.LocationInformation;
import iris.location_service.dto.LocationOverviewDto;
import iris.location_service.search.db.DBSearchIndex;
import iris.location_service.search.db.LocationRepository;
import iris.location_service.search.db.model.Location;
import iris.location_service.search.db.model.LocationIdentifier;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Service
public class LocationService {

	private final @NotNull ModelMapper mapper;

	private final @NotNull LocationRepository locationRepository;

	private final @NotNull DBSearchIndex index;

	public void addLocations(String providerId, List<LocationInformation> locations) {
		// TODO: Authenticate API Access

		// TODO: Define sensible limits for this API

		var data = locations.stream().map(entry -> {
			return getLocationFromLocationInformation(providerId, entry);
		}).collect(Collectors.toList());

		locationRepository.saveAll(data);
	}

	private Location getLocationFromLocationInformation(String providerId, LocationInformation entry) {

		// Reset possibly sent provider id. We need to ensure this comes from
		// the authentication system and isn't user-provided!
		entry.setProviderId(providerId.toString());
		var location = mapper.map(entry, Location.class);

		// For the search index, we are only interested in a subset of the data structure for location information
		// Can be replaced
		location.setId(new LocationIdentifier(providerId, entry.getId()));

		return location;
	}

	public List<LocationOverviewDto> getProviderLocations(String providerId) {

		List<Location> providerLocations = locationRepository.findByIdProviderId(providerId.toString());

		return providerLocations.stream().map(location -> {
			return new LocationOverviewDto(location.getId().getLocationId(), location.getName());
		}).collect(Collectors.toList());

	}

	public boolean deleteLocation(String providerId, String locationId) {
		// Construct a new ID to match the (provider, id) pair key
		LocationIdentifier ident = new LocationIdentifier(providerId.toString(), locationId);

		Optional<Location> match = locationRepository.findById(ident);
		if (match.isPresent()) {
			locationRepository.delete(match.get());
			return true;
		}
		return false;
	}

	public Page<LocationInformation> search(String searchKeyword, Pageable pageable) {
		return index.search(searchKeyword, pageable);
	}

	public Optional<LocationInformation> getLocationByProviderIdAndLocationId(String providerId, String locationId) {
		var ident = new LocationIdentifier(providerId, locationId);

		return locationRepository.findById(ident)
				.map(this::toDto);
	}

	private LocationInformation toDto(Location it) {

		var location = mapper.map(it, LocationInformation.class);

		location.setId(it.getId().getLocationId());
		location.setProviderId(it.getId().getProviderId());

		return location;
	}
}
