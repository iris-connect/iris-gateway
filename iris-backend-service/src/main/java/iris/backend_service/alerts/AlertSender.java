package iris.backend_service.alerts;

import iris.backend_service.alerts.slack.SlackMessageCreator;
import iris.backend_service.alerts.zammad.ZammadTicketCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author Jens Kutzsche
 */
@Slf4j
@Service()
@RequiredArgsConstructor
class AlertSender {

	private final AlertRepository alertRepo;
	private final SlackMessageCreator slack;
	private final ZammadTicketCreator zammad;

	@Scheduled(fixedDelay = 15000)
	public void sendAlerts() {

		log.trace("Alert - Creator - send alert invoked");

		var alerts = alertRepo.findAll();

		var forDelete = alerts.stream()
				.filter(this::createAlert)
				.collect(Collectors.toList());

		log.debug("JSON-RPC - sent successfully: {}; not sent {}", forDelete.size(), alerts.size() - forDelete.size());

		alertRepo.deleteAll(forDelete);

		log.trace("Alert - Creator - alerts sent and deleted");
	}

	private boolean createAlert(Alert alert) {

		switch (alert.getAlertType()) {
			case MESSAGE:
				return createAlertMessage(alert);
			case TICKET:
				return sendAlertTicket(alert);
			default:
				return false;
		}
	}

	private boolean createAlertMessage(Alert alert) {
		return slack.createMessage(alert);
	}

	private boolean sendAlertTicket(Alert alert) {
		return zammad.createTicket(alert);
	}
}
