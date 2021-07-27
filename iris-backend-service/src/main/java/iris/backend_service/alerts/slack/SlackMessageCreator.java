package iris.backend_service.alerts.slack;

import static org.apache.commons.lang3.StringUtils.*;

import iris.backend_service.alerts.Alert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

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

	public boolean createMessage(Alert alert) {

		if ("DISABLED".equals(properties.getToken())) {
			return true;
		}

		// Initialize an API Methods client with the given token
		var methods = slack.methods(properties.getToken());

		List<LayoutBlock> blocks = List.of(
				SectionBlock.builder()
						.text(
								MarkdownTextObject.builder()
										.text("*" + alert.getClient() + " → " + alert.getTitle() + "*")
										.build())
						.build(),
				SectionBlock.builder()
						.text(MarkdownTextObject.builder()
								.text(alert.getText())
								.build())
						.build(),
				SectionBlock.builder()
						.text(MarkdownTextObject.builder()
								.text("_(Version: " + alert.getVersion() + ")_")
								.build())
						.build());

		// Build a request object
		var request = ChatPostMessageRequest.builder()
				.channel(properties.getChannel()) // Use a channel ID `C1234567` is preferrable
				.text(firstNonBlank(alert.getTitle(), alert.getText()))
				.blocks(blocks)
				.build();

		// Get a response as a Java object
		try {

			var response = methods.chatPostMessage(request);

			if (response.isOk()) {
				return true;
			}

			log.error("Alert - Slack - message could not be sent => Errors: {}", response.getErrors());
			return false;

		} catch (IOException | SlackApiException e) {

			log.error("Alert - Slack - message could not be sent => Exception", e);
			return false;
		}
	}
}
