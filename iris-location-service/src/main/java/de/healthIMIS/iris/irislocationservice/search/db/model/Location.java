package de.healthIMIS.iris.irislocationservice.search.db.model;

import de.healthIMIS.iris.irislocationservice.dto.LocationContact;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
