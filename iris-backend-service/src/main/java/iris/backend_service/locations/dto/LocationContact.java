package iris.backend_service.locations.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class LocationContact {

	@NotBlank
	private String officialName = null;

	@NotBlank
	private String representative = null;

	@NotNull
	@Valid
	private LocationAddress address = null;

	private String ownerEmail = null;

	@NotBlank
	private String email = null;

	@NotBlank
	private String phone = null;

}
