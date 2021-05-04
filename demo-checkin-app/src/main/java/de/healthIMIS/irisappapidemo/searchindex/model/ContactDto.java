package de.healthIMIS.irisappapidemo.searchindex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDto {

    String officialName;
    String representative;
    AddressDto address;
    String ownerEmail;
    String email;
    String phone;

}
