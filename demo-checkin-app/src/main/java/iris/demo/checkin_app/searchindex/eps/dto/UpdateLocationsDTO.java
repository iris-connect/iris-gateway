package iris.demo.checkin_app.searchindex.eps.dto;

import iris.demo.checkin_app.searchindex.model.LocationDto;
import iris.demo.checkin_app.searchindex.model.LocationsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class UpdateLocationsDTO {

    private UUID providerId;
    private List<LocationDto> locations;
}
