package iris.backend_service.messages;

import iris.backend_service.jsonrpc.JsonRpcClientDto;
import iris.backend_service.messages.zammad.ZammadTicketCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
class MessageRPCImpl implements MessageRPC {

	private final MessageRepository messageRepo;
	private final ModelMapper mapper;
	private final ZammadTicketCreator zammad;
	private final FeedbackMessageConverter feedbackConverter;

	@Override
	public String postAlerts(@Valid JsonRpcClientDto client, @Valid List<AlertDto> alertDtos) {

		var clientName = client.getName();

		log.trace("Alert - JSON-RPC - Post alert invoked for client: {} with {} alerts", clientName,
				alertDtos.size());

		var messages = alertDtos.stream()
				.map(it -> mapper.map(it, Message.class))
				.peek(it -> it.setClient(clientName))
				.toList();

		try {

			messageRepo.saveAllAndFlush(messages);

			log.debug("Alert - JSON-RPC - Post alert for client: {} => result: OK", clientName);

			return "OK";

		} catch (Exception e) {

			log.error(String.format("Alert - JSON-RPC - Can't save alert for client: %s => result: ", clientName), e);

			return "ERROR";
		}
	}

	@Override
	public FeedbackResponseDto postFeedback(@Valid JsonRpcClientDto client, @Valid FeedbackDto request) {

		var message = feedbackConverter.convertToMessage(request);
		message.setClient(client.getName());

		var result = zammad.createTicket(message);

		if (result.isError()) {
			throw new RuntimeException("Can't sent the feedback to the target system.");
		}

		return new FeedbackResponseDto(result.getIdFromSystem().orElse("DISABLED"));
	}
}
