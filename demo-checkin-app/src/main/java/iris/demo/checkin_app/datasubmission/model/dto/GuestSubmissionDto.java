package iris.demo.checkin_app.datasubmission.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GuestSubmissionDto {

	private String secret;

	private String keyReference;

	private String encryptedData;

}
