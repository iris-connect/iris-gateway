package iris.backend_service.alerts;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import iris.backend_service.alerts.Alert.AlertType;
import iris.backend_service.alerts.slack.SlackMessageCreator;
import iris.backend_service.alerts.zammad.ZammadTicketCreator;
import iris.backend_service.jsonrpc.JsonRpcClientDto;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Jens Kutzsche
 */
@SpringBootTest
@ActiveProfiles("dev_env")
class AlertRPCTests {

	@Autowired
	AlertRPC sut;

	@SpyBean
	AlertRepository repo;

	@MockBean
	SlackMessageCreator slack;

	@MockBean
	ZammadTicketCreator zammad;

	@Captor
	ArgumentCaptor<List<Alert>> alertsCaptor;
	@Captor
	ArgumentCaptor<Alert> alertCaptor;

	@AfterEach
	void resetSpy() {
		reset(repo);
	}

	@Test
	void testMessage() throws Throwable {

		var client = new JsonRpcClientDto();
		client.setName("client");

		var alert = new AlertDto("Title", "Text", "Source", "1.0", AlertType.MESSAGE);

		sut.postAlerts(client, List.of(alert));

		verify(repo).saveAllAndFlush(alertsCaptor.capture());

		var value = alertsCaptor.getValue();
		assertThat(value).hasSize(1)
				.element(0).extracting("title", "text", "alertType").contains("Title", "Text", AlertType.MESSAGE);

		Thread.sleep(16000);

		verify(slack).createMessage(alertCaptor.capture());

		var value2 = alertCaptor.getValue();
		assertThat(value2).extracting("title", "text", "alertType").contains("Title", "Text", AlertType.MESSAGE);
	}

	@Test
	void testTicket() throws Throwable {

		var client = new JsonRpcClientDto();
		client.setName("client");

		var alert = new AlertDto("Title", "Text", "Source", "1.0", AlertType.TICKET);

		sut.postAlerts(client, List.of(alert));

		verify(repo).saveAllAndFlush(alertsCaptor.capture());

		var value = alertsCaptor.getValue();
		assertThat(value).hasSize(1)
				.element(0).extracting("title", "text", "alertType").contains("Title", "Text", AlertType.TICKET);

		Thread.sleep(16000);

		verify(zammad).createTicket(alertCaptor.capture());

		var value2 = alertCaptor.getValue();
		assertThat(value2).extracting("title", "text", "alertType").contains("Title", "Text", AlertType.TICKET);
	}
}
