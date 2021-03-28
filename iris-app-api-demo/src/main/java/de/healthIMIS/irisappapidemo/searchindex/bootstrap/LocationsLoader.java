package de.healthIMIS.irisappapidemo.searchindex.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.healthIMIS.irisappapidemo.searchindex.model.LocationDto;
import de.healthIMIS.irisappapidemo.searchindex.model.LocationsDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.xml.stream.Location;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class LocationsLoader {

    @SneakyThrows
    public LocationsDto getDemoLocations() {

        List<LocationDto> locations = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        File dir = new File("src/main/resources/bootstrap/locations");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File location : directoryListing) {
                LocationDto locationDto = objectMapper.readValue(
                        location,
                        LocationDto.class);
                locations.add(locationDto);
            }
        }

        return LocationsDto.builder().locations(locations).build();
    }

}