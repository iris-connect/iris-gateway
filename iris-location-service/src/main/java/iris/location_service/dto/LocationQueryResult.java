package iris.location_service.dto;

import javax.validation.constraints.NotNull;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationQueryResult {

	@NotNull
	private List<LocationInformation> locations;

	private long totalElements;

	private int size;

	private int page;
}
