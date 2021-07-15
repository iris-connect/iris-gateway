
package iris.demo.contact_diary_app.submission.rpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
	String name;
	String phone;
	Address address;
	String additionalInformation;
}
