package de.healthIMIS.iris.irislocationservice.search;

import de.healthIMIS.iris.irislocationservice.dto.LocationInformation;
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
