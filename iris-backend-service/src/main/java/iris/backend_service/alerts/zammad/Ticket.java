package iris.backend_service.alerts.zammad;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author Jens Kutzsche
 */
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
class Ticket {
	private final String title;
	private final String group;
	private final String customer_id;
	private final Article article;

	private String id;
}
