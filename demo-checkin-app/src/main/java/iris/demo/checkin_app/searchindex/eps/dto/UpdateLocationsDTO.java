package iris.demo.checkin_app.searchindex.eps.dto;

import iris.demo.checkin_app.searchindex.model.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UpdateLocationsDTO {

	private List<LocationDto> locations;
}
