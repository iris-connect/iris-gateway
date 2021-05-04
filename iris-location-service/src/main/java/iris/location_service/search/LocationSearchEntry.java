package iris.location_service.search;

import iris.location_service.dto.LocationInformation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LocationSearchEntry {

    private String providerId;

    private LocationInformation locationInformation;

}