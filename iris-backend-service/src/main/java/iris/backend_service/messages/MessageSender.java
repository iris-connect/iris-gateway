package iris.backend_service.messages;

import iris.backend_service.messages.slack.SlackMessageCreator;
import iris.backend_service.messages.zammad.ZammadTicketCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Jens Kutzsche
 */
@Slf4j
@Component
@RequiredArgsConstructor
class MessageSender {

	private final MessageRepository messageRepo;
	private final SlackMessageCreator slack;
	private final ZammadTicketCreator zammad;

	@Scheduled(fixedDelay = 15000)
	public void sendMessages() {

		log.trace("Message - Creator - send messages invoked");

		var forDelete = messageRepo.findAll().stream()
				.filter(this::sendMessage)
				.collect(Collectors.toList());

		log.debug("JSON-RPC - sent successfully: {}; not sent {}", forDelete.size(),
				messageRepo.findAll().size() - forDelete.size());

		messageRepo.deleteAll(forDelete);

		log.trace("Message - Creator - messages sent and deleted");
	}

	private boolean sendMessage(Message message) {

		switch (message.getMessageType()) {
			case MESSAGE:
				return createSlackMessage(message);
			case TICKET:
				return createZammadTicket(message);
			case FEEDBACK:
				return createZammadTicket(message);
			default:
				return false;
		}
	}

	boolean createSlackMessage(Message message) {
		return !slack.createMessage(message).isError();
	}

	boolean createZammadTicket(Message message) {
		return !zammad.createTicket(message).isError();
	}
}
