package iris.backend_service.messages;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Email;

/**
 * This class represents the json structure of a request to EPS. It is also the input json of Iris-Client-Backend uses
 * this structure.
 * 
 * @author Ostfalia Gruppe 12
 * @author Jens Kutzsche
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Getter
@Setter(value = AccessLevel.PACKAGE)
public class FeedbackDto {

	private @NonNull String category;

	private @NonNull String title;

	private @NonNull String comment;

	private String name;

	@Email
	private String email;

	private String organisation;

	private String browserName;

	private String browserResolution;

	private @NonNull String sourceApp;

	private @NonNull String appVersion;
}
