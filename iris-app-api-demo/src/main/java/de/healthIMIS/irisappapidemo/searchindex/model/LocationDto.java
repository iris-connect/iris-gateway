package de.healthIMIS.irisappapidemo.searchindex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDto {

    UUID id;
    String name;
    String publicKey;
    ContactDto contact;
    List<ContextDto> contexts;

}
