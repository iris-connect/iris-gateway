package iris.backend_service.messages.slack;

import static org.apache.commons.lang3.StringUtils.*;

import iris.backend_service.messages.Message;
import iris.backend_service.messages.MessageSendingResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;

/**
 * @author Jens Kutzsche
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SlackMessageCreator {

	Slack slack = Slack.getInstance();

	private final SlackProperties properties;

	public MessageSendingResult createMessage(Message message) {

		if (StringUtils.equalsAny("DISABLED", properties.getToken(), properties.getChannel())) {
			return MessageSendingResult.disabled();
		}

		// Initialize an API Methods client with the given token
		var methods = slack.methods(properties.getToken());

		List<LayoutBlock> blocks = List.of(
				SectionBlock.builder()
						.text(
								MarkdownTextObject.builder()
										.text("*" + message.getSourceApp() + " → " + message.getTitle() + "*")
										.build())
						.build(),
				SectionBlock.builder()
						.text(MarkdownTextObject.builder()
								.text(message.getText())
								.build())
						.build(),
				SectionBlock.builder()
						.text(MarkdownTextObject.builder()
								.text("_(Client: " + message.getClient() + "  |  App-Version: " + message.getAppVersion() + ")_")
								.build())
						.build());

		// Build a request object
		var request = ChatPostMessageRequest.builder()
				.channel(properties.getChannel()) // Use a channel ID `C1234567` is preferrable
				.text(firstNonBlank(message.getTitle(), message.getText()))
				.blocks(blocks)
				.build();

		// Get a response as a Java object
		try {

			var response = methods.chatPostMessage(request);

			if (response.isOk()) {

				log.debug("Message - Slack - message sent");
				return MessageSendingResult.successful();
			}

			log.error("Message - Slack - message could not be sent => Errors: {}", response.getErrors());
			return MessageSendingResult.withError();

		} catch (IOException | SlackApiException e) {

			log.error("Message - Slack - message could not be sent => Exception", e);
			return MessageSendingResult.withError();
		}
	}
}
