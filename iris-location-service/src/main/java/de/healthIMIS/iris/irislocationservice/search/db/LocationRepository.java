package de.healthIMIS.iris.irislocationservice.search.db;


import de.healthIMIS.iris.irislocationservice.search.db.model.Location;
import de.healthIMIS.iris.irislocationservice.search.db.model.LocationIdentifier;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, LocationIdentifier> {

    List<Location> findByNameContaining(String query);

}

