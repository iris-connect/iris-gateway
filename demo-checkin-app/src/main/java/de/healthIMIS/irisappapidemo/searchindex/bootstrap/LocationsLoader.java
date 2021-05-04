package de.healthIMIS.irisappapidemo.searchindex.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.healthIMIS.irisappapidemo.config.ResourceHelper;
import de.healthIMIS.irisappapidemo.searchindex.model.LocationDto;
import de.healthIMIS.irisappapidemo.searchindex.model.LocationsDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;

import javax.xml.stream.Location;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class LocationsLoader {

    private ResourceHelper resourceLoader;

    private ObjectMapper objectMapper;

    @SneakyThrows
    public LocationsDto getDemoLocations() {

        List<LocationDto> locations = new ArrayList<>();

        var resources = resourceLoader.loadResources("classpath:bootstrap/locations/*.json");

        for (var resource: resources){
            var location = objectMapper.readValue(resource.getInputStream(), LocationDto.class);
            locations.add(location);
        }

        return LocationsDto.builder().locations(locations).build();
    }

}