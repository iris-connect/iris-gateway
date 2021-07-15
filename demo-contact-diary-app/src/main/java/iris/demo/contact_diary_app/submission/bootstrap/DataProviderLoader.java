package iris.demo.contact_diary_app.submission.bootstrap;

import iris.demo.contact_diary_app.config.ResourceHelper;
import iris.demo.contact_diary_app.submission.rpc.dto.CaseDataProvider;
import iris.demo.contact_diary_app.utils.ObjectMapperWithTimeModule;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataProviderLoader {

    @NonNull private final ResourceHelper resourceHelper;

    @NonNull private final ObjectMapperWithTimeModule objectMapper;

    @SneakyThrows
    public CaseDataProvider getDataProvider() {

        var resource = resourceHelper.loadResource("classpath:bootstrap/dataprovider.json");

        return objectMapper.readValue(resource.getInputStream(), CaseDataProvider.class);
    }

}
