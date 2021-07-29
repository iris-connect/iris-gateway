package iris.backend_service.locations.search;

import iris.backend_service.locations.dto.LocationInformation;
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