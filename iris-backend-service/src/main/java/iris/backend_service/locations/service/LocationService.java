package iris.backend_service.locations.service;

import static org.apache.commons.lang3.StringUtils.*;

import iris.backend_service.locations.dto.LocationInformation;
import iris.backend_service.locations.dto.LocationOverviewDto;
import iris.backend_service.locations.search.db.DBSearchIndex;
import iris.backend_service.locations.search.db.LocationRepository;
import iris.backend_service.locations.search.db.model.Location;
import iris.backend_service.locations.search.db.model.LocationIdentifier;
import iris.backend_service.locations.utils.ValidationHelper;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LocationService {

	private final @NotNull ModelMapper mapper;

	private final @NotNull LocationRepository locationRepo;

	private final @NotNull DBSearchIndex index;

	public List<String> addLocations(String providerId, List<LocationInformation> locations) {

		var listOfInvalidLocations = new ArrayList<String>();
		var listOfValidLocations = new ArrayList<Location>();

		for (LocationInformation locationInformation : locations) {

			var location = getLocationFromLocationInformation(providerId, locationInformation);

			location.setContactPhone(normalizeSpace(location.getContactPhone()));

			if (location.getName() != null
					&& location.getContactRepresentative() != null
					&& (ValidationHelper.isValidAndNotNullEmail(location.getContactEmail())
							|| ValidationHelper.isValidAndNotNullPhoneNumber(location.getContactPhone()))) {

				listOfValidLocations.add(location);

			} else {
				listOfInvalidLocations.add(locationInformation.getId());
			}
		}

		locationRepo.saveAll(listOfValidLocations);

		return listOfInvalidLocations;
	}

	private Location getLocationFromLocationInformation(String providerId, LocationInformation entry) {
		// Reset possibly sent provider id. We need to ensure this comes from
		// the authentication system and isn't user-provided!
		entry.setProviderId(providerId);
		var location = mapper.map(entry, Location.class);

		// For the search index, we are only interested in a subset of the data structure for location information
		// Can be replaced
		location.setId(new LocationIdentifier(providerId, entry.getId()));

		return location;
	}

	public List<LocationOverviewDto> getProviderLocations(String providerId) {

		var providerLocations = locationRepo.findByIdProviderId(providerId.toString());

		return providerLocations
				.map(location -> new LocationOverviewDto(location.getId().getLocationId(), location.getName())).toList();

	}

	public boolean deleteLocation(String providerId, String locationId) {
		// Construct a new ID to match the (provider, id) pair key
		var ident = new LocationIdentifier(providerId, locationId);

		Optional<Location> match = locationRepo.findById(ident);
		if (match.isPresent()) {
			locationRepo.delete(match.get());
			return true;
		}
		return false;
	}

	public Page<LocationInformation> search(String searchKeyword, Pageable pageable) {
		return index.search(searchKeyword, pageable);
	}

	public Optional<LocationInformation> getLocationByProviderIdAndLocationId(String providerId, String locationId) {
		var ident = new LocationIdentifier(providerId, locationId);

		return locationRepo.findById(ident).map(this::toDto);
	}

	private LocationInformation toDto(Location it) {

		var location = mapper.map(it, LocationInformation.class);

		location.setId(it.getId().getLocationId());
		location.setProviderId(it.getId().getProviderId());

		return location;
	}
}
