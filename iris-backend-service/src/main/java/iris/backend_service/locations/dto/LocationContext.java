package iris.backend_service.locations.dto;

import iris.backend_service.core.validation.NoSignOfAttack;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LocationContext {

	@NoSignOfAttack
	String id = null;

	@NoSignOfAttack
	String name = null;
}
