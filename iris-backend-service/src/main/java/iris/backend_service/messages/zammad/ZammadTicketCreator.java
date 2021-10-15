package iris.backend_service.messages.zammad;

import static org.apache.commons.lang3.StringUtils.*;
import static org.springframework.http.MediaType.*;

import iris.backend_service.messages.Message;
import iris.backend_service.messages.MessageSendingResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Jens Kutzsche
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ZammadTicketCreator {

	private final ZammadProperties properties;

	public MessageSendingResult createTicket(Message message) {

		if (StringUtils.equalsAny("DISABLED", properties.getToken(), properties.getEnvironment())) {
			return MessageSendingResult.disabled();
		}

		var customerId = "guess:";
		if (StringUtils.isNotBlank(message.getSender())) {
			customerId += message.getSender();
		} else {
			customerId += properties.getCustomerIds().getOrDefault(message.getSourceApp(),
					"unknown@message.iris-connect.de");
		}

		var text = replace(message.getText(), "\n", "<br />");
		text += String.format("<br /><br /><i>Client: %s<br />App: %s<br />App-Version: %s</i>", message.getClient(),
				message.getSourceApp(), message.getAppVersion());

		var ticket = new Ticket(message.getTitle(), properties.getGroup(), customerId, new Article(text));

		var responseOpt = WebClient.create(properties.getTicketUri().toString())
				.post()
				.headers(this::setHeaders)
				.bodyValue(ticket)
				.retrieve()
				.toEntity(Ticket.class)
				.blockOptional();

		if (responseOpt.isEmpty()) {

			log.error("Message - Zammad - ticket could not be created => http call ends without result");
			return MessageSendingResult.withError();
		}

		var response = responseOpt.get();
		if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
			log.error("Message - Zammad - ticket could not be created => Errors: {}", response.getBody());
			return MessageSendingResult.withError();
		}

		var envTag = new Tag(properties.getEnvironment(), response.getBody().getId());

		var response2Opt = WebClient.create(properties.getTagUri().toString())
				.post()
				.headers(this::setHeaders)
				.bodyValue(envTag)
				.retrieve()
				.toEntity(String.class)
				.blockOptional();

		if (response2Opt.isEmpty()) {

			log.error("Message - Zammad - tag could not be created => http call ends without result");
			return MessageSendingResult.withError();
		}

		var response2 = responseOpt.get();

		if (response2.getStatusCode().is2xxSuccessful()) {

			log.debug("Message - Zammad - ticket created");
			return MessageSendingResult.ofIdFromSystem(response.getBody().getNumber());
		}

		log.error("Message - Zammad - tag could not be created => Errors: {}", response2.getBody());
		return MessageSendingResult.withError();
	}

	private void setHeaders(HttpHeaders headers) {
		headers.setContentType(APPLICATION_JSON);
		headers.setAccept(List.of(APPLICATION_JSON));
		headers.setBearerAuth(properties.getToken());
	}
}
