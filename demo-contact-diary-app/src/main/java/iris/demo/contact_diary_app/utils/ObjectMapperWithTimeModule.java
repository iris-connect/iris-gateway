package iris.demo.contact_diary_app.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperWithTimeModule extends ObjectMapper {
    public ObjectMapperWithTimeModule() {
        registerModule(new JavaTimeModule());
    }
}
