package iris.feedback_service.feedback_submission.model;

import lombok.NonNull;

/*
 * This class represents the needed json structure of a git request.
 * @author Ostfalia Gruppe 12
 */
public class GitIssueRequestDto {

	private @NonNull String title;
	private @NonNull String label;
	private @NonNull String body;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
