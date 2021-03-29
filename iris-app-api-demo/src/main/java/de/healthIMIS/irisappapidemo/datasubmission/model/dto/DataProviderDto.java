package de.healthIMIS.irisappapidemo.datasubmission.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataProviderDto {

    private String name;

    private AddressDto address;

}
