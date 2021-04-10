package de.healthIMIS.iris.irislocationservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import org.springframework.validation.annotation.Validated;

/**
 * LocationInformation
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-27T12:26:11.318Z[GMT]")


@NoArgsConstructor
@Getter
@Setter
public class LocationInformation   {

  private String id = null;

  private String providerId = null;

  private String name = null;

  private String publicKey = null;

  private LocationContact contact = null;

  private List<LocationContext> contexts = null;

}
