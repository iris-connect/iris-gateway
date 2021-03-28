package de.healthIMIS.iris.irislocationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * LocationInformation
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-27T12:26:11.318Z[GMT]")


@NoArgsConstructor
@Getter
@Setter
public class LocationInformation   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("publicKey")
  private String publicKey = null;

  @JsonProperty("contact")
  private LocationContact contact = null;

  @JsonProperty("contexts")
  @Valid
  private List<LocationContext> contexts = null;

}
