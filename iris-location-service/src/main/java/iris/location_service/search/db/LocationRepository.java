package iris.location_service.search.db;

import iris.location_service.search.db.model.Location;
import iris.location_service.search.db.model.LocationIdentifier;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, LocationIdentifier> {

	List<Location> findByNameContainingIgnoreCase(String query);

	List<Location> findByIdProviderId(String providerId);
}
