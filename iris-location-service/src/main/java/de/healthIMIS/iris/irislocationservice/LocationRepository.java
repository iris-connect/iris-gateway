package de.healthIMIS.iris.irislocationservice;


import de.healthIMIS.iris.irislocationservice.model.Location;
import de.healthIMIS.iris.irislocationservice.model.LocationIdentifier;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, LocationIdentifier> {}

