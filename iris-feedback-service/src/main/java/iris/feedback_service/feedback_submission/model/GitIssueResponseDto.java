package iris.feedback_service.feedback_submission.model;

import lombok.NonNull;

/*
 * This class represents the needed json structure of a git response.
 * @author Ostfalia Gruppe 12
 */
public class GitIssueResponseDto {

	private @NonNull String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
