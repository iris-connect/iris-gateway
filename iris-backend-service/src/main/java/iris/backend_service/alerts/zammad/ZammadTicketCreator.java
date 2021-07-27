package iris.backend_service.alerts.zammad;

import iris.backend_service.alerts.Alert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jens Kutzsche
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ZammadTicketCreator {

	private final RestTemplate rest;
	private final ZammadProperties properties;

	@PostConstruct
	void postConstruct() {

		rest.getInterceptors().add(new ClientHttpRequestInterceptor() {

			@Override
			public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
					throws IOException {

				var headers = request.getHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.setAccept(List.of(MediaType.APPLICATION_JSON));
				headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + properties.getToken());

				return execution.execute(request, body);
			}
		});
	}

	public boolean createTicket(Alert alert) {

		if ("DISABLED".equals(properties.getToken())) {
			return true;
		}

		var customerId = "guess:" + properties.getCustomerIds().getOrDefault(alert.getSourceApp(),
				"unknown@alert.iris-connect.de");
		var text = alert.getText() + "<br /><br /><i>Client: " + alert.getClient() + "<br />App-Version: "
				+ alert.getAppVersion() + "</i>";

		var ticket = new Ticket(alert.getTitle(), properties.getGroup(), customerId, new Article(text));

		var response = rest.postForEntity(properties.getTicketUri(), ticket, Ticket.class);

		if (!response.getStatusCode().is2xxSuccessful()) {
			log.error("Alert - Zammad - ticket could not be created => Errors: {}", response.getBody());
			return false;
		}

		var envTag = new Tag(properties.getEnvironment(), response.getBody().getId());
		var response2 = rest.postForEntity(properties.getTagUri(), envTag, String.class);

		if (response2.getStatusCode().is2xxSuccessful()) {
			return true;
		}

		log.error("Alert - Zammad - tag could not be created => Errors: {}", response.getBody());
		return false;
	}
}
