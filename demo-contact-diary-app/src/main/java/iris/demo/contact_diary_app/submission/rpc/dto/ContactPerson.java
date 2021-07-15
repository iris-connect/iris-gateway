
package iris.demo.contact_diary_app.submission.rpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactPerson {
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private Sex sex;
	private String email;
	private String phone;
	private String mobilePhone;
	private Address address;
	WorkPlace workPlace;
	ContactInformation contactInformation;
}
