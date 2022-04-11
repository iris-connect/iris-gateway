package iris.backend_service.locations.dto;

import iris.backend_service.core.validation.NoSignOfAttack;
import iris.backend_service.core.validation.NoSignOfAttack.Phone;
import iris.backend_service.core.validation.PhoneNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class LocationContact {

	@NotBlank
	@NoSignOfAttack
	private String officialName = null;

	@NotBlank
	@NoSignOfAttack
	private String representative = null;

	@NotNull
	@Valid
	private LocationAddress address = null;

	@Email
	@NoSignOfAttack
	private String ownerEmail = null;

	@NotBlank
	@Email
	@NoSignOfAttack
	private String email = null;

	@NotBlank
	@PhoneNumber
	@NoSignOfAttack(payload = Phone.class)
	private String phone = null;
}
