package iris.backend_service.locations.dto;

import iris.backend_service.core.validation.NoSignOfAttack;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class LocationInformation {

	@NotBlank
	@NoSignOfAttack
	String id = null;

	@NotBlank
	@NoSignOfAttack
	String providerId = null;

	@NotBlank
	@NoSignOfAttack
	String name = null;

	@NoSignOfAttack
	String publicKey = null;

	@NotNull
	@Valid
	LocationContact contact = null;

	List<@Valid LocationContext> contexts = null;
}
