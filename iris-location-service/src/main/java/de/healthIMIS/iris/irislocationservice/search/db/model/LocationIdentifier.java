package de.healthIMIS.iris.irislocationservice.search.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocationIdentifier implements Serializable {

    private String providerId;

    private String locationId;

}
