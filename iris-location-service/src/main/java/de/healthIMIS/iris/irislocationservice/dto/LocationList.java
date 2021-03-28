package de.healthIMIS.iris.irislocationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * LocationList
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-27T12:26:11.318Z[GMT]")


@AllArgsConstructor
public class LocationList   {
  @JsonProperty("locations")
  @Valid
  private List<LocationInformation> locations = new ArrayList<LocationInformation>();

  public LocationList locations(List<LocationInformation> locations) {
    this.locations = locations;
    return this;
  }

  public LocationList addLocationsItem(LocationInformation locationsItem) {
    this.locations.add(locationsItem);
    return this;
  }

  /**
   * Get locations
   * @return locations
   **/
  @Schema(required = true, description = "")
      @NotNull
    @Valid
    public List<LocationInformation> getLocations() {
    return locations;
  }

  public void setLocations(List<LocationInformation> locations) {
    this.locations = locations;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LocationList locationList = (LocationList) o;
    return Objects.equals(this.locations, locationList.locations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(locations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LocationList {\n");
    
    sb.append("    locations: ").append(toIndentedString(locations)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
