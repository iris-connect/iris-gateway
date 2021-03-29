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

    @Autowired
    private DataProviderLoader dataProviderLoader;

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
        DataProviderDto dataProvider = dataProviderLoader.getDataProvider();
        GuestListDto guestList = GuestListDto.builder().guests(guests).additionalInformation("").startDate(LocalDateTime.now()).endDate(LocalDateTime.now().plusHours(6)).dataProvider(dataProvider).build();
        GuestListEncryptor encryptor = GuestListEncryptor.builder().guestList(guestList).givenPublicKey("MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAtcEUFlnEZfDkPO/mxXwC\n" +
                "NmNTjwlndnp4fk521W+lPqhQ5f8lipp6A2tnIhPeLtvwVN6q68hzASaWxbhAypp2\n" +
                "Bv77YRjoDacqx4gaq2eLGepb01CHNudGGvQGwhTYbfa8k13d2+z9+uN0/SrmofGc\n" +
                "ZvhjZWnxALGcdZRUn3Trk3O6uYrBODVk6HmpFMZKqL9tKtrCxLG17Yr9ek53sFJI\n" +
                "7YuEKxFbvF/20w5rcPYsmGgkoNjGhq2ajdGwe5UfcsGOEE/4tGScF2GMZM/Tjsh9\n" +
                "W9wISn6/e1v/Hhj9I19UfgasbQrE9lC1D01i1kTCjpYQ+dWcnA1Ulj2evymPpq9H\n" +
                "XVoKl8LmsfQ7n9w0vAfY2sPNW3H3wN/NlcuZslUTeTopZxtt4j7b7i+7Ik62t7Yr\n" +
                "CrioWQOlsWYME2YChzDCp6IlBOjeZtA9IDh6V3hbnDlV4AZygoMWWUWb1WS3oFNf\n" +
                "vaJfolxZRZXDUrsrQYpJPUZ8BexE0OPEdNNS8KCa9ANbhgO3xvSTSsPpbE7P346k\n" +
                "zyTCQxyJM66FLGu7vmGyt1sGiUBXFQCVYbSFNT3opke70f9/rYyzZoVA4ZBbAK7F\n" +
                "azMTNzUZzt9EICWupkdrDEcyuFQ3Q/9a8Bp14zAciIAujpWNMaeO9zi57W9V0Vd4\n" +
                "LyPpQIplL03J5EtG6FLHRWECAwEAAQ==").build();
        log.info(encryptor.encrypt());
        log.info(encryptor.getSecretKeyBase64());
    }
}
