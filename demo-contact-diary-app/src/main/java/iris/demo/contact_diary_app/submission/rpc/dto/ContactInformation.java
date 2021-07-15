
package iris.demo.contact_diary_app.submission.rpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactInformation {
	Instant firstContactDate;
	Instant lastContactDate;
	ContactCategory contactCategory;
	String basicConditions;
}
