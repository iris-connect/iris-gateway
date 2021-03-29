package de.healthIMIS.irisappapidemo.searchindex.web.client;

import de.healthIMIS.irisappapidemo.searchindex.bootstrap.LocationsLoader;
import de.healthIMIS.irisappapidemo.searchindex.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SearchIndexClientTest {

    @Autowired
    SearchIndexClient client;

    @Autowired
    LocationsLoader locationsLoader;

    @Test
    void testUpdateLocations() {

        LocationsDto locations = locationsLoader.getDemoLocations();

        client.updateLocations(locations);

    }

    @Test
    void testDeleteLocation() {

        LocationsDto locations = locationsLoader.getDemoLocations();

        client.deleteLocation(locations.getLocations().get(1));

    }
}