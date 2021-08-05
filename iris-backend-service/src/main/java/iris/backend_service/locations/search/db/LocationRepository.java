package iris.backend_service.locations.search.db;

import iris.backend_service.locations.search.db.model.Location;
import iris.backend_service.locations.search.db.model.LocationIdentifier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

public interface LocationRepository extends JpaRepository<Location, LocationIdentifier> {

	Page<Location> findByNameContainingIgnoreCase(String query, Pageable pageable);

	Streamable<Location> findByIdProviderId(String providerId);

	default Page<Location> searchLocations(String query, Pageable pageable) {
		return findByNameContainingIgnoreCase(query, pageable);
	}
}
