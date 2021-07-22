package iris.backend_service.alerts.slack;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jens Kutzsche
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SlackMessageCreator {

	private final RestTemplate rest;
	private final SlackProperties properties;

	public void createTicket(String ticketTitle, String noteSubject, String message) {

		// var ticket = new Ticket(ticketTitle, properties.getGroup(), new Article(noteSubject, message),
		// properties.getCustomer());
		//
		// var resp = rest.postForEntity(properties.getApiAddress(), ticket, null);
		//
		// System.out.println(resp);
	}
}
