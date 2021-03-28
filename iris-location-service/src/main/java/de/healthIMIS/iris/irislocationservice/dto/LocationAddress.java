package de.healthIMIS.iris.irislocationservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LocationAddress {

    private String street = null;

    private String city = null;

    private String zip = null;

}
