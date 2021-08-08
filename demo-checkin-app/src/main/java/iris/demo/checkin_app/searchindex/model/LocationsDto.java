package iris.demo.checkin_app.searchindex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationsDto {

	private List<LocationDto> locations;

	@JsonIgnore
	public Integer getCount() {
		return locations.size();
	}

	public LocationDto getLocationById(UUID uuid) {
		return locations.stream().filter(it -> it.getId().equals(uuid)).findFirst().get();
	}
}
