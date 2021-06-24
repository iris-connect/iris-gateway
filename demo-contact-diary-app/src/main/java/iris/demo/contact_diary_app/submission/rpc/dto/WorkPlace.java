
package iris.demo.contact_diary_app.submission.rpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkPlace {
	String name;
	String pointOfContact;
	String phone;
	Address address;
}
