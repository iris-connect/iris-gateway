package iris.backend_service.messages;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import iris.backend_service.jsonrpc.JsonRpcClientDto;
import iris.backend_service.messages.AlertDto.AlertType;
import iris.backend_service.messages.Message.MessageType;
import iris.backend_service.messages.zammad.ZammadTicketCreator;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Jens Kutzsche
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev_env")
class MessageRPCTests {

	@Autowired
	MessageRPC sut;

	@SpyBean
	MessageRepository repo;

	@SpyBean
	MessageSender sender;

	@SpyBean
	ZammadTicketCreator zammadCreator;

	@Captor
	ArgumentCaptor<List<Message>> messagesCaptor;
	@Captor
	ArgumentCaptor<Message> messageCaptor;

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

		verify(repo).saveAllAndFlush(messagesCaptor.capture());

		var value = messagesCaptor.getValue();
		assertThat(value).hasSize(1)
				.element(0).extracting("title", "text", "messageType").contains("Title", "Text", MessageType.MESSAGE);

		sender.sendMessages();

		verify(sender).createSlackMessage(messageCaptor.capture());

		var value2 = messageCaptor.getValue();
		assertThat(value2).extracting("title", "text", "messageType").contains("Title", "Text", MessageType.MESSAGE);
	}

	@Test
	void testTicket() throws Throwable {

		var client = new JsonRpcClientDto();
		client.setName("client");

		var alert = new AlertDto("Title", "Text", "Source", "1.0", AlertType.TICKET);

		sut.postAlerts(client, List.of(alert));

		verify(repo).saveAllAndFlush(messagesCaptor.capture());

		var value = messagesCaptor.getValue();
		assertThat(value).hasSize(1)
				.element(0).extracting("title", "text", "messageType").contains("Title", "Text", MessageType.TICKET);

		sender.sendMessages();

		verify(sender).createZammadTicket(messageCaptor.capture());

		var value2 = messageCaptor.getValue();
		assertThat(value2).extracting("title", "text", "messageType").contains("Title", "Text", MessageType.TICKET);
	}

	@Test
	void testFeedback() throws Throwable {

		var client = new JsonRpcClientDto();
		client.setName("client");

		var feedback = new FeedbackDto("Category", "Title", "Comment", "Name", "Email", "Org", "Browser", "BVersion",
				"Source", "1.0");

		sut.postFeedback(client, feedback);

		verify(repo, never()).saveAllAndFlush(any());

		verify(zammadCreator).createTicket(messageCaptor.capture());

		var value = messageCaptor.getValue();
		assertThat(value).extracting("title", "messageType", "sourceApp", "appVersion")
				.contains("Title - Category", MessageType.FEEDBACK, "Source", "1.0");
		assertThat(value.getText()).contains("Comment");
	}
}
