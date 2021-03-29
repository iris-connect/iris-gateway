package de.healthIMIS.irisappapidemo.datasubmission.bootstrap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.healthIMIS.irisappapidemo.datasubmission.model.dto.AddressDto;
import de.healthIMIS.irisappapidemo.datasubmission.model.dto.DataProviderDto;
import de.healthIMIS.irisappapidemo.datasubmission.model.dto.GuestDto;
import de.healthIMIS.irisappapidemo.datasubmission.encryption.GuestListEncryptor;
import de.healthIMIS.irisappapidemo.datasubmission.model.dto.GuestListDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest
@Slf4j
class GuestLoaderTest {

    @Autowired
    private GuestLoader guestLoader;

    @Test
    List<GuestDto> getGuests() {
        List<GuestDto> guests = guestLoader.getGuests();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult = null;
        try {
            jsonResult = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(guests);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return guests;
    }

    @Test
    void encryptGuestList() {
        List<GuestDto> guests = getGuests();
        AddressDto dataProviderAddress = AddressDto.builder().street("Europaplatz 5").city("Darmstadt").houseNumber("5").zipCode("64293").build();
        DataProviderDto dataProvider = DataProviderDto.builder().name("SmartMeeting").address(dataProviderAddress).build();
        GuestListDto guestList = GuestListDto.builder().guests(guests).additionalInformation("").startDate(LocalDateTime.now()).endDate(LocalDateTime.now().plusHours(6)).dataProvider(dataProvider).build();
        GuestListEncryptor encryptor = GuestListEncryptor.builder().guestList(guestList).givenPublicKey("DemoKey").build();
        log.info(encryptor.encrypt());
        log.info(encryptor.getSecretKeyBase64());
    }
}
