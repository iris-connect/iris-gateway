package iris.demo.checkin_app.searchindex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationDto {

	UUID id;
	String name;
	String publicKey;
	ContactDto contact;
	List<ContextDto> contexts;

}
