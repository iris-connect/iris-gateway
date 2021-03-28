package de.healthIMIS.iris.irislocationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Embeddable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
public class LocationContact   {

  private String officialName = null;

  private String representative = null;

  private LocationAddress address = null;

  private String ownerEmail = null;

  private String email = null;

  private String phone = null;

}
