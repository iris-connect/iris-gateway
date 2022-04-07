package iris.demo.checkin_app.datasubmission.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestDto {

	private String firstName;
	private String lastName;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	private SexDto sex;

	private String email;
	private String phone;
	private String mobilePhone;
	private AddressDto address;
	private AttendanceInformationDto attendanceInformation;
	private boolean identityChecked;

}
