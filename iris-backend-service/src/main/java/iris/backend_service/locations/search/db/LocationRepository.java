package iris.backend_service.locations.search.db;

import iris.backend_service.locations.search.db.model.Location;
import iris.backend_service.locations.search.db.model.LocationIdentifier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

public interface LocationRepository extends JpaRepository<Location, LocationIdentifier> {

	Streamable<Location> findByIdProviderId(String providerId);
}
