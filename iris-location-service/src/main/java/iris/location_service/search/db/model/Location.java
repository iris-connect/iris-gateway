package iris.location_service.search.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedNativeQuery(name = "Location.update.tokens",
	query = "UPDATE location SET tokens = to_tsvector('german',  CONCAT_WS(' ', name, contact_Official_Name, contact_Representative, contact_Address_Street, contact_Address_City, contact_Address_Zip, contact_Owner_Email, contact_Email, contact_Phone))")
public class Location {

  @EmbeddedId
  private LocationIdentifier id;

  private String name;

  private String contactOfficialName;

  private String contactRepresentative;

  private String contactAddressStreet;

  private String contactAddressCity;

  private String contactAddressZip;

  private String contactOwnerEmail;

  private String contactEmail;

  private String contactPhone;

}
