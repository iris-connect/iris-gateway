package iris.demo.contact_diary_app.submission.bootstrap;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import iris.demo.contact_diary_app.config.ResourceHelper;
import iris.demo.contact_diary_app.submission.rpc.dto.Events;
import iris.demo.contact_diary_app.utils.ObjectMapperWithTimeModule;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EventsLoader {

    @NonNull private final ResourceHelper resourceHelper;
    @NonNull private final ObjectMapperWithTimeModule objectMapper;


    @SneakyThrows
    public Events getEvents() {

        var resource = resourceHelper.loadResource("classpath:bootstrap/event_list.json");

        return objectMapper.readValue(resource.getInputStream(), Events.class);
    }

}