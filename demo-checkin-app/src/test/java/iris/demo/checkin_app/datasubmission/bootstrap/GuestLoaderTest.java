package iris.demo.checkin_app.datasubmission.bootstrap;

import static org.junit.jupiter.api.Assertions.*;

import iris.demo.checkin_app.IrisWebIntegrationTest;
import iris.demo.checkin_app.datasubmission.bootstrap.DataProviderLoader;
import iris.demo.checkin_app.datasubmission.bootstrap.GuestLoader;
import iris.demo.checkin_app.datasubmission.encryption.GuestListEncryptor;
import iris.demo.checkin_app.datasubmission.model.dto.DataProviderDto;
import iris.demo.checkin_app.datasubmission.model.dto.GuestDto;
import iris.demo.checkin_app.datasubmission.model.dto.GuestListDto;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@IrisWebIntegrationTest
class GuestLoaderTest {

    @Autowired
    private GuestLoader guestLoader;

    @Autowired
    private DataProviderLoader dataProviderLoader;

    @Test
    List<GuestDto> getGuests() {
    	
        List<GuestDto> guests = guestLoader.getGuests();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(guests);
        } catch (JsonProcessingException e) {
            fail(e);
        }
        return guests;
    }

    @Test
    void encryptGuestList() {
        List<GuestDto> guests = getGuests();
        DataProviderDto dataProvider = dataProviderLoader.getDataProvider();
        GuestListDto guestList = GuestListDto.builder().guests(guests).additionalInformation("").startDate(Instant.now()).endDate(Instant.now().plus(6,ChronoUnit.HOURS)).dataProvider(dataProvider).build();
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
    }
}
