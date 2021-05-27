package iris.location_service.search.db;

import static org.junit.jupiter.api.Assertions.*;

import iris.location_service.search.db.model.Location;
import iris.location_service.search.db.model.LocationIdentifier;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LocationRepositoryTest {

  @Autowired
  private LocationRepository repo;

  @Test
  public void demoForUsingTheRepo() {

	// when
	var id = new LocationIdentifier("provider", "location");
	var location = new Location();
	location.setId(id);
	repo.save(location);

	// then
	var res = repo.findById(new LocationIdentifier("provider", "location"));

	// assert
	assertEquals("location", res.get().getId().getLocationId());
  }
}
