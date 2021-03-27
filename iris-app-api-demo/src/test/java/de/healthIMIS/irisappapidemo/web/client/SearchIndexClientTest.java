package de.healthIMIS.irisappapidemo.web.client;

import de.healthIMIS.irisappapidemo.searchindex.bootstrap.LocationsLoader;
import de.healthIMIS.irisappapidemo.searchindex.model.AddressDto;
import de.healthIMIS.irisappapidemo.searchindex.model.ContactDto;
import de.healthIMIS.irisappapidemo.searchindex.model.ContextDto;
import de.healthIMIS.irisappapidemo.searchindex.model.LocationDto;
import de.healthIMIS.irisappapidemo.searchindex.web.client.SearchIndexClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class SearchIndexClientTest {

    @Autowired
    SearchIndexClient client;

    @Autowired
    LocationsLoader locationsLoader;

    @Test
    void testUpdateLocations() {

        List<LocationDto> locations = locationsLoader.getDemoLocations();

        client.updateLocations(locations);

    }
}