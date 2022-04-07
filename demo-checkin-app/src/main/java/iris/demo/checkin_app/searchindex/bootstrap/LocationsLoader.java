package iris.demo.checkin_app.searchindex.bootstrap;

import iris.demo.checkin_app.config.ResourceHelper;
import iris.demo.checkin_app.searchindex.model.LocationDto;
import iris.demo.checkin_app.searchindex.model.LocationsDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
@Component
public class LocationsLoader {

	@Value("${use-naughty-location:false}")
	private boolean useNaughtyLocation;

	private final ResourceHelper resourceLoader;

	private final ObjectMapper objectMapper;

	@SneakyThrows
	public LocationsDto getDemoLocations() {

		var locations = new ArrayList<LocationDto>();

		var resources = resourceLoader.loadResources("classpath:bootstrap/locations/*.json");

		for (var resource : resources) {

			if (useNaughtyLocation || !"a-naughty-place.json".equals(resource.getFilename())) {

				var location = objectMapper.readValue(resource.getInputStream(), LocationDto.class);
				locations.add(location);
			}
		}

		return LocationsDto.builder().locations(locations).build();
	}
}
