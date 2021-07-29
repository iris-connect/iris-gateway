package iris.backend_service.alerts.zammad;

import static org.springframework.http.MediaType.*;

import iris.backend_service.alerts.Alert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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

	public boolean createTicket(Alert alert) {

		if ("DISABLED".equals(properties.getToken())) {
			return true;
		}

		var customerId = "guess:" + properties.getCustomerIds().getOrDefault(alert.getSourceApp(),
				"unknown@alert.iris-connect.de");
		var text = alert.getText() + "<br /><br /><i>Client: " + alert.getClient() + "<br />App-Version: "
				+ alert.getAppVersion() + "</i>";

		var ticket = new Ticket(alert.getTitle(), properties.getGroup(), customerId, new Article(text));

		var responseOpt = WebClient.create(properties.getTicketUri().toString())
				.post()
				.headers(this::setHeaders)
				.bodyValue(ticket)
				.retrieve()
				.toEntity(Ticket.class)
				.blockOptional();

		if (responseOpt.isEmpty()) {

			log.error("Alert - Zammad - ticket could not be created => http call ends without result");
			return false;
		}

		var response = responseOpt.get();
		if (!response.getStatusCode().is2xxSuccessful()) {
			log.error("Alert - Zammad - ticket could not be created => Errors: {}", response.getBody());
			return false;
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

			log.error("Alert - Zammad - tag could not be created => http call ends without result");
			return false;
		}

		var response2 = responseOpt.get();

		if (response2.getStatusCode().is2xxSuccessful()) {
			return true;
		}

		log.error("Alert - Zammad - tag could not be created => Errors: {}", response2.getBody());
		return false;
	}

	private void setHeaders(HttpHeaders headers) {
		headers.setContentType(APPLICATION_JSON);
		headers.setAccept(List.of(APPLICATION_JSON));
		headers.setBearerAuth(properties.getToken());
	}
}
