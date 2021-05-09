package iris.demo.checkin_app.datasubmission.eps.dto;

import iris.demo.checkin_app.datasubmission.model.dto.GuestListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class DataSubmissionDto {

	private UUID requestId;
	private GuestListDto guestList;

}
