package iris.backend_service.messages;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

/**
 * @author Jens Kutzsche
 */
@Validated
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class AlertDto {

	@Size(max = 100)
	String title;
	@Size(max = 1000)
	String text;
	@Size(max = 50)
	String sourceApp;
	@Size(max = 50)
	String appVersion;
	AlertType alertType;

	public enum AlertType {
		TICKET, MESSAGE
	}
}
