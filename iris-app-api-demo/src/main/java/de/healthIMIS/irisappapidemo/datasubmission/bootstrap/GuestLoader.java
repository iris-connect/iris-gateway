package de.healthIMIS.irisappapidemo.datasubmission.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.healthIMIS.irisappapidemo.datasubmission.model.dto.GuestDto;
import de.healthIMIS.irisappapidemo.datasubmission.model.dto.GuestListDto;
import de.healthIMIS.irisappapidemo.searchindex.model.LocationDto;
import de.healthIMIS.irisappapidemo.searchindex.model.LocationsDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class GuestLoader {

    @SneakyThrows
    public List<GuestDto> getGuests() {

        List<GuestDto> guests = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/bootstrap/guests/guest_list.json");

        guests = objectMapper.registerModule(new JavaTimeModule()).readValue(
                        file, new TypeReference<List<GuestDto>>(){});

        return guests;
    }

}