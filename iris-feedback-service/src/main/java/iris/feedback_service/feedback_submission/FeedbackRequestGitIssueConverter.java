package iris.feedback_service.feedback_submission;

import iris.feedback_service.feedback.web.DataFeedbackRequestDto;
import iris.feedback_service.feedback_submission.model.GitIssueRequestDto;

import org.springframework.stereotype.Service;

/*
 * This class converts a request to a git issue dto.
 * @author Ostfalia Gruppe 12
 */
@Service
public class FeedbackRequestGitIssueConverter {

	/*
	 * Converts request object to git issue object.
	 * @param request object of Iris-Client-Backend
	 * @return git issue object
	 */
	public GitIssueRequestDto convertRequestToGitIssue(DataFeedbackRequestDto request) {

		var gitIssue = new GitIssueRequestDto();
		gitIssue.setTitle(getGitIssueTitle(request));
		gitIssue.setLabel(getGitIssueLabel(request));
		gitIssue.setBody(getGitIssueBody(request));
		return gitIssue;
	}

	/*
	 * Extracts title information of a request object.
	 * @param request object of Iris-Client-Backend
	 * @return title as string
	 */
	private String getGitIssueTitle(DataFeedbackRequestDto request) {

		StringBuilder title = new StringBuilder();
		title.append(request.getTitle()).append(" - ").append(request.getCategory());
		return title.toString();
	}

	/*
	 * Extracts label information of a request object.
	 * @param request object of Iris-Client-Backend
	 * @return label as string
	 */
	private String getGitIssueLabel(DataFeedbackRequestDto request) {
		return "bug";
	}

	/*
	 * Extracts body information of a request object.
	 * @param request object of Iris-Client-Backend
	 * @return body as string
	 */
	private String getGitIssueBody(DataFeedbackRequestDto request) {

		StringBuilder comment = new StringBuilder();

		if (request.getName() != null) {
			comment.append(request.getName());
		}

		if (request.getEmail() != null) {

			if (comment.length() > 0) {
				comment.append(" - ").append(request.getEmail());
			} else {
				comment.append(request.getEmail());
			}
		}

		if (request.getOrganisation() != null) {

			if (comment.length() > 0) {
				comment.append(" - ").append(request.getOrganisation());
			} else {
				comment.append(request.getOrganisation());
			}
		}

		if (comment.length() > 0) {
			comment.append("\n +++++++++++++++++++++++++++++++++++++ \n");
		}

		if (request.getBrowserName() != null) {
			comment.append("Browser: ").append(request.getBrowserName());
		}

		if (request.getBrowserResolution() != null && request.getBrowserName() != null) {
			comment.append("  -  ").append("Resolution: ").append(request.getBrowserResolution());
		} else if (request.getBrowserResolution() != null) {
			comment.append("Resolution: ").append(request.getBrowserResolution());
		}

		if (request.getBrowserName() != null || request.getBrowserResolution() != null) {
			comment.append("\n +++++++++++++++++++++++++++++++++++++ \n");
		}

		comment.append(request.getComment());
		return comment.toString();
	}
}
