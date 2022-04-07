package iris.demo.contact_diary_app.utils;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class ObjectMapperWithTimeModule extends ObjectMapper {
	public ObjectMapperWithTimeModule() {
		registerModule(new JavaTimeModule());
	}
}
