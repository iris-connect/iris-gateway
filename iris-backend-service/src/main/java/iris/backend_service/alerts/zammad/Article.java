package iris.backend_service.alerts.zammad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Jens Kutzsche
 */
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
class Article {
	private final String body;
	private String content_type = "text/html";
	private boolean internal = true;
}
