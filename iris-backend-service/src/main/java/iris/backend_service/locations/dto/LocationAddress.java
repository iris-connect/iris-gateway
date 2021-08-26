package iris.backend_service.locations.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
@Validated
public class LocationAddress {

	@NotBlank
	private String street = null;

	@NotBlank
	private String city = null;

	@NotBlank
	private String zip = null;

}
