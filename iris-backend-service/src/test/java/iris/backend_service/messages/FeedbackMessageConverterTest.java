package iris.backend_service.messages;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class FeedbackMessageConverterTest {

	private @InjectMocks FeedbackMessageConverter converter;

	private FeedbackDto feedback;

	@BeforeAll
	void init() {

		feedback = new FeedbackDto();
		feedback.setTitle("That's a test tile!");
		feedback.setCategory("My Category");
		feedback.setComment("That's a test comment!");
		feedback.setName("Name Surname");
		feedback.setBrowserName("firefox");
	}

	@Test
	void testGitIssueConverter() {

		var message = converter.convertToMessage(feedback);
		assertThat(message.getTitle(), containsString(feedback.getTitle()));
		assertThat(message.getTitle(), containsString(feedback.getCategory()));
		assertThat(message.getText(), containsString(feedback.getComment()));
		assertThat(message.getText(), containsString(feedback.getName()));
		assertThat(message.getText(), containsString(feedback.getBrowserName()));
	}
}
