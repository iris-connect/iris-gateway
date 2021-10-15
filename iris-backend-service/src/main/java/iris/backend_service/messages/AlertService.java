package iris.backend_service.messages;

import iris.backend_service.messages.Message.MessageBuilder;
import iris.backend_service.messages.Message.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

/**
 * @author Jens Kutzsche
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AlertService {

	private final MessageRepository alertRepo;

	private static final String APP = "iris-bs";
	private String version = "";
	{
		try {
			var properties = PropertiesLoaderUtils.loadAllProperties("git.properties");
			version = properties.getProperty("git.build.version", "-") + " ("
					+ properties.getProperty("git.commit.id.abbrev", "-")
					+ ")";
		} catch (IOException e) {
			log.error("Can't load git properties", e);
		}
	}

	/**
	 * Creates a message in the communication system of the IRIS-Connect project. This should be used for less serious
	 * violations and suspected abuse.
	 * <p>
	 * The text is also logged locally by the method!
	 * </p>
	 */
	public void createAlertMessage(String title, String text) {

		log.warn("Alert: {} - {}", title, text);

		saveAlert(List.of(message(title, text)));
	}

	/**
	 * Creates a ticket in the ticket system of the IRIS-Connect project in addition to the message. This should be used
	 * for serious violations and sure abuses.
	 * <p>
	 * The text is also logged locally by the method!
	 * </p>
	 */
	public void createAlertTicketAndMessage(String title, String text) {

		log.warn("Alert: {} - {}", title, text);

		saveAlert(List.of(message(title, text), ticket(title, text)));
	}

	private Message message(String title, String text) {
		return createAlertBuilder(title, text).messageType(MessageType.MESSAGE).build();
	}

	private Message ticket(String title, String text) {
		return createAlertBuilder(title, text).messageType(MessageType.TICKET).build();
	}

	private MessageBuilder createAlertBuilder(String title, String text) {
		return Message.builder()
				.title(title)
				.text(text)
				.sourceApp(APP)
				.appVersion(version)
				.client(APP);
	}

	private void saveAlert(List<Message> alerts) {

		try {

			alertRepo.saveAllAndFlush(alerts);

			log.debug("Alert - Service - save alert => result: OK");

		} catch (Exception e) {

			log.error("Alert - Service - Can't save alert => result: ", e);
		}
	}
}
