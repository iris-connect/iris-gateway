package de.healthIMIS.irisappapidemo.startup;

import de.healthIMIS.irisappapidemo.searchindex.bootstrap.LocationsLoader;
import de.healthIMIS.irisappapidemo.searchindex.model.LocationDto;
import de.healthIMIS.irisappapidemo.searchindex.model.LocationsDto;
import de.healthIMIS.irisappapidemo.searchindex.web.client.SearchIndexClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Slf4j
public class LocationUpdater {

    @Autowired
    private LocationsLoader locationsLoader;

    @Autowired
    private SearchIndexClient searchIndexClient;


    @PostConstruct
    public void init() {
        LocationsDto locations = locationsLoader.getDemoLocations();

        log.info(String.format("Updating %d locations", locations.getCount()));
        searchIndexClient.updateLocations(locations);

        log.info(String.format("Delete location %s", locations.getLocationByIndex(1).getName()));
        searchIndexClient.deleteLocation(locations.getLocationByIndex(1));
    }


}
