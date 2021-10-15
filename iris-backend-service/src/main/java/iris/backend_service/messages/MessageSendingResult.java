package iris.backend_service.messages;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * @author Jens Kutzsche
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MessageSendingResult {

	private boolean disabled;
	private boolean error;
	private Optional<String> idFromSystem;

	public static MessageSendingResult disabled() {
		return new MessageSendingResult(true, false, Optional.empty());
	}

	public static MessageSendingResult withError() {
		return new MessageSendingResult(false, true, Optional.empty());
	}

	public static MessageSendingResult ofIdFromSystem(String id) {
		return new MessageSendingResult(false, false, Optional.of(id));
	}

	public static MessageSendingResult successful() {
		return new MessageSendingResult(false, false, Optional.empty());
	}
}
