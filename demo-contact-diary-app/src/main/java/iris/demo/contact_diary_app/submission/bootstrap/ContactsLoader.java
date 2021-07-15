package iris.demo.contact_diary_app.submission.bootstrap;

import iris.demo.contact_diary_app.config.ResourceHelper;
import iris.demo.contact_diary_app.submission.rpc.dto.Contacts;
import iris.demo.contact_diary_app.utils.ObjectMapperWithTimeModule;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ContactsLoader {

    @NonNull private final ResourceHelper resourceHelper;
    @NonNull private final ObjectMapperWithTimeModule objectMapper;


    @SneakyThrows
    public Contacts getContacts() {

        var resource = resourceHelper.loadResource("classpath:bootstrap/contact_list.json");

        return objectMapper.readValue(resource.getInputStream(), Contacts.class);
    }

}