package iris.backend_service.locations.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import org.springframework.validation.annotation.Validated;

/**
 * LocationInformation
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
		date = "2021-03-27T12:26:11.318Z[GMT]")

@NoArgsConstructor
@Getter
@Setter
public class LocationInformation {

	String id = null;

	String providerId = null;

	String name = null;

	String publicKey = null;

	LocationContact contact = null;

	List<LocationContext> contexts = null;

}
