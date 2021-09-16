package iris.backend_service.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

/**
 * This class represents the json structure of a response of EPS. The input json will be mapped on this class after a
 * post request for feedback data has been sent.
 * 
 * @author Ostfalia Gruppe 12
 * @author Jens Kutzsche
 */
@AllArgsConstructor
@Getter
public class FeedbackResponseDto {

	private @NonNull String issueNumber;
}
