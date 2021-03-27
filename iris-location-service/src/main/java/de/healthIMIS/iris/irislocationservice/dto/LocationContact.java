package de.healthIMIS.iris.irislocationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Kontaktperson des Standorts
 */
@Schema(description = "Kontaktperson des Standorts")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-27T12:26:11.318Z[GMT]")


public class LocationContact   {
  @JsonProperty("officialName")
  private String officialName = null;

  @JsonProperty("representative")
  private String representative = null;

  @JsonProperty("address")
  private LocationAddress address = null;

  @JsonProperty("ownerEmail")
  private String ownerEmail = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("phone")
  private String phone = null;

  public LocationContact officialName(String officialName) {
    this.officialName = officialName;
    return this;
  }

  /**
   * Offizieller Unternehmensname
   * @return officialName
   **/
  @Schema(example = "Darfichrein GmbH", description = "Offizieller Unternehmensname")
  
    public String getOfficialName() {
    return officialName;
  }

  public void setOfficialName(String officialName) {
    this.officialName = officialName;
  }

  public LocationContact representative(String representative) {
    this.representative = representative;
    return this;
  }

  /**
   * Ansprechpartner für dieses Unternehmen
   * @return representative
   **/
  @Schema(example = "Silke ", description = "Ansprechpartner für dieses Unternehmen")
  
    public String getRepresentative() {
    return representative;
  }

  public void setRepresentative(String representative) {
    this.representative = representative;
  }

  public LocationContact address(LocationAddress address) {
    this.address = address;
    return this;
  }

  /**
   * Get address
   * @return address
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public LocationAddress getAddress() {
    return address;
  }

  public void setAddress(LocationAddress address) {
    this.address = address;
  }

  public LocationContact ownerEmail(String ownerEmail) {
    this.ownerEmail = ownerEmail;
    return this;
  }

  /**
   * E-Mail des Inhabers
   * @return ownerEmail
   **/
  @Schema(example = "covid@restaurant.de", description = "E-Mail des Inhabers")
  
    public String getOwnerEmail() {
    return ownerEmail;
  }

  public void setOwnerEmail(String ownerEmail) {
    this.ownerEmail = ownerEmail;
  }

  public LocationContact email(String email) {
    this.email = email;
    return this;
  }

  /**
   * ggf. E-Mail einer weiteren Kontaktperson
   * @return email
   **/
  @Schema(example = "covid2@restaurant.de", description = "ggf. E-Mail einer weiteren Kontaktperson")
  
    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocationContact phone(String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * Telefonnummer eines Ansprechpartners
   * @return phone
   **/
  @Schema(example = "die bleibt privat :-)", description = "Telefonnummer eines Ansprechpartners")
  
    public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LocationContact locationContact = (LocationContact) o;
    return Objects.equals(this.officialName, locationContact.officialName) &&
        Objects.equals(this.representative, locationContact.representative) &&
        Objects.equals(this.address, locationContact.address) &&
        Objects.equals(this.ownerEmail, locationContact.ownerEmail) &&
        Objects.equals(this.email, locationContact.email) &&
        Objects.equals(this.phone, locationContact.phone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(officialName, representative, address, ownerEmail, email, phone);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LocationContact {\n");
    
    sb.append("    officialName: ").append(toIndentedString(officialName)).append("\n");
    sb.append("    representative: ").append(toIndentedString(representative)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    ownerEmail: ").append(toIndentedString(ownerEmail)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
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
