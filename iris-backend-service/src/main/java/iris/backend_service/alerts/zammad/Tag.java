package iris.backend_service.alerts.zammad;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Jens Kutzsche
 */
@RequiredArgsConstructor
@Getter
public class Tag {
	private final String item;
	private final String o_id;
	private String object = "Ticket";
}
