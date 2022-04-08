package iris.backend_service.locations.dto;

import iris.backend_service.core.validation.NoSignOfAttack;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class LocationAddress {

	@NotBlank
	@NoSignOfAttack
	private String street = null;

	@NotBlank
	@NoSignOfAttack
	private String city = null;

	@NotBlank
	@NoSignOfAttack
	private String zip = null;
}
