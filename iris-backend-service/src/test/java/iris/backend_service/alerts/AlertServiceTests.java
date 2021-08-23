package iris.backend_service.alerts;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import iris.backend_service.MemoryAppender;
import iris.backend_service.alerts.Alert.AlertType;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Jens Kutzsche
 */
@SpringBootTest
@ActiveProfiles("dev_env")
class AlertServiceTests {

	@Autowired
	AlertService sut;

	@MockBean
	AlertRepository repo;

	@Captor
	ArgumentCaptor<List<Alert>> alertCaptor;

	private MemoryAppender memoryAppender;

	@BeforeEach
	public void setup() {
		Logger logger = (Logger) LoggerFactory.getLogger(AlertService.class);
		memoryAppender = new MemoryAppender();
		memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
		logger.setLevel(Level.DEBUG);
		logger.addAppender(memoryAppender);
		memoryAppender.start();
	}

	@Test
	void testMessage() throws Throwable {

		sut.createAlertMessage("Title", "Text");

		assertThat(memoryAppender.countEventsForLogger(AlertService.class)).isEqualTo(2);
		assertThat(memoryAppender.contains("Alert: Title - Text", Level.WARN)).isTrue();

		verify(repo).saveAllAndFlush(alertCaptor.capture());

		var value = alertCaptor.getValue();
		assertThat(value).hasSize(1)
				.element(0).extracting("title", "text", "alertType").contains("Title", "Text", AlertType.MESSAGE);
	}

	@Test
	void testTicketAndMessage() throws Throwable {

		sut.createAlertTicketAndMessage("Title", "Text");

		assertThat(memoryAppender.countEventsForLogger(AlertService.class)).isEqualTo(2);
		assertThat(memoryAppender.contains("Alert: Title - Text", Level.WARN)).isTrue();

		verify(repo).saveAllAndFlush(alertCaptor.capture());

		var value = alertCaptor.getValue();
		assertThat(value).hasSize(2)
				.extracting("title", "text", "alertType")
				.contains(tuple("Title", "Text", AlertType.MESSAGE),
						tuple("Title", "Text", AlertType.TICKET));
	}
}
