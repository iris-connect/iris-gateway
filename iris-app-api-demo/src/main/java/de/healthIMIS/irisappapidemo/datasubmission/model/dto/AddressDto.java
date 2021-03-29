package de.healthIMIS.irisappapidemo.datasubmission.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {

    private String street;
    private String houseNumber;
    private String zipCode;
    private String city;

}
