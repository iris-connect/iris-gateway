package de.healthIMIS.irisappapidemo.datasubmission.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.healthIMIS.irisappapidemo.datasubmission.model.dto.DataProviderDto;
import de.healthIMIS.irisappapidemo.datasubmission.model.dto.GuestDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DataProviderLoader {

    @SneakyThrows
    public DataProviderDto getDataProvider() {

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/bootstrap/dataprovider/smartmeeting.json");

        return objectMapper.registerModule(new JavaTimeModule()).readValue(
                file, DataProviderDto.class);
    }

}
