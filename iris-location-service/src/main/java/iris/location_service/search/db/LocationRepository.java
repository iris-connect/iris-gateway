package iris.location_service.search.db;


import org.springframework.data.repository.CrudRepository;

import iris.location_service.search.db.model.Location;
import iris.location_service.search.db.model.LocationIdentifier;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, LocationIdentifier> {
    List<Location> findByNameContaining(String query);

    List<Location> findByIdProviderId(String providerId);
}

