package iris.demo.checkin_app.searchindex.eps.dto;

import iris.demo.checkin_app.searchindex.model.LocationDto;
import iris.demo.checkin_app.searchindex.model.LocationsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class DeleteLocationDTO {

    private UUID providerId;
    private String locationId;
}
