package iris.backend_service.alerts.zammad;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Jens Kutzsche
 */
@RequiredArgsConstructor
@Getter
class Ticket {
	private final String title;
	private final String group;
	private final Article article;
	private final String customer;
}
