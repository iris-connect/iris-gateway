package iris.demo.checkin_app.searchindex.eps.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DeleteLocationDTO {

	private String locationId;
}
