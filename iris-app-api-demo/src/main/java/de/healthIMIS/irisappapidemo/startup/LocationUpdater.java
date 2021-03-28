package de.healthIMIS.irisappapidemo.startup;

import de.healthIMIS.irisappapidemo.searchindex.bootstrap.LocationsLoader;
import de.healthIMIS.irisappapidemo.searchindex.model.LocationDto;
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
        List<LocationDto> locations = locationsLoader.getDemoLocations();

        log.info(String.format("Updating %d locations", locations.size()));
        searchIndexClient.updateLocations(locations);

        log.info(String.format("Delete location %s", locations.get(1).getName()));
        searchIndexClient.deleteLocation(locations.get(1));
    }


}
