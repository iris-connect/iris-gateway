package iris.backend_service.locations.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LocationContact {

	private String officialName = null;

	private String representative = null;

	private LocationAddress address = null;

	private String ownerEmail = null;

	private String email = null;

	private String phone = null;

}
