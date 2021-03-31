package de.healthIMIS.irisappapidemo.datasubmission.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.healthIMIS.irisappapidemo.config.ResourceHelper;
import de.healthIMIS.irisappapidemo.datasubmission.model.dto.DataProviderDto;
import de.healthIMIS.irisappapidemo.datasubmission.model.dto.GuestDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DataProviderLoader {

    @NonNull private ResourceHelper resourceHelper;

    @NonNull private ObjectMapper objectMapper;

    @SneakyThrows
    public DataProviderDto getDataProvider() {

        var resource = resourceHelper.loadResource("classpath:bootstrap/dataprovider/smartmeeting.json");

        return objectMapper.registerModule(new JavaTimeModule()).readValue(
                resource.getInputStream(), DataProviderDto.class);
    }

}
