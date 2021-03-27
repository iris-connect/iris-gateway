package de.healthIMIS.iris.irislocationservice.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

/**
 * Anschrift des Standorts
 */
@Schema(description = "Anschrift des Standorts")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-27T12:26:11.318Z[GMT]")


public class LocationAddress   {
  @JsonProperty("street")
  private String street = null;

  @JsonProperty("city")
  private String city = null;

  @JsonProperty("zip")
  private String zip = null;

  public LocationAddress street(String street) {
    this.street = street;
    return this;
  }

  /**
   * street + number
   * @return street
   **/
  @Schema(example = "Türkenstr. 7", required = true, description = "street + number")
      @NotNull

    public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public LocationAddress city(String city) {
    this.city = city;
    return this;
  }

  /**
   * Stadt
   * @return city
   **/
  @Schema(example = "München", required = true, description = "Stadt")
      @NotNull

    public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public LocationAddress zip(String zip) {
    this.zip = zip;
    return this;
  }

  /**
   * Postleitzahl
   * @return zip
   **/
  @Schema(example = "80333", required = true, description = "Postleitzahl")
      @NotNull

    public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LocationAddress locationAddress = (LocationAddress) o;
    return Objects.equals(this.street, locationAddress.street) &&
        Objects.equals(this.city, locationAddress.city) &&
        Objects.equals(this.zip, locationAddress.zip);
  }

  @Override
  public int hashCode() {
    return Objects.hash(street, city, zip);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LocationAddress {\n");
    
    sb.append("    street: ").append(toIndentedString(street)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    zip: ").append(toIndentedString(zip)).append("\n");
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
