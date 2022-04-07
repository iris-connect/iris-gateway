package iris.backend_service.messages;

import iris.backend_service.messages.Message.MessageType;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * This class converts a request to a git issue dto.
 *
 * @author Ostfalia Gruppe 12
 * @author Jens Kutzsche
 */
@Service
public class FeedbackMessageConverter {

	/**
	 * Converts request object to git issue object.
	 *
	 * @param feedback object of Iris-Client-Backend
	 * @return git issue object
	 */
	public Message convertToMessage(FeedbackDto feedback) {

		return Message.builder()
				.title(getTitle(feedback))
				.text(getText(feedback))
				.messageType(MessageType.FEEDBACK)
				.sourceApp(feedback.getSourceApp())
				.appVersion(feedback.getAppVersion())
				.sender(feedback.getEmail())
				.build();
	}

	/**
	 * Extracts title information of a request object.
	 *
	 * @param request object of Iris-Client-Backend
	 * @return title as string
	 */
	private String getTitle(FeedbackDto request) {
		return String.format("%s - %s", request.getTitle(), request.getCategory());
	}

	/*
	 * Extracts body information of a request object.
	 * @param request object of Iris-Client-Backend
	 * @return body as string
	 */
	private String getText(FeedbackDto request) {

		var init = Stream.of(
				request.getName(),
				request.getEmail(),
				request.getOrganisation())
				.filter(StringUtils::isNotBlank).collect(Collectors.joining(" - "));

		StringBuilder comment = new StringBuilder(init);

		if (comment.length() > 0) {
			comment.append("\n +++++++++++++++++++++++++++++++++++++ \n");
		}

		if (request.getBrowserName() != null) {
			comment.append("Browser: ").append(request.getBrowserName());
		}

		if (request.getBrowserResolution() != null) {
			if (request.getBrowserName() != null) {
				comment.append("  -  ");
			}

			comment.append("Resolution: ").append(request.getBrowserResolution());
		}

		if (request.getBrowserName() != null || request.getBrowserResolution() != null) {
			comment.append("\n +++++++++++++++++++++++++++++++++++++ \n");
		}

		comment.append(request.getComment());

		return comment.toString();
	}
}
