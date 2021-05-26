package iris.location_service.search.db;

import iris.location_service.search.db.model.Location;
import iris.location_service.search.db.model.LocationIdentifier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

public interface LocationRepository extends JpaRepository<Location, LocationIdentifier> {

  Page<Location> findByNameContainingIgnoreCase(String query, Pageable pageable);

  Streamable<Location> findByIdProviderId(String providerId);
}
