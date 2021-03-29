package de.healthIMIS.irisappapidemo.datasubmission.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.healthIMIS.irisappapidemo.datarequest.model.dto.LocationDataRequestDto;
import de.healthIMIS.irisappapidemo.datasubmission.model.dto.GuestSubmissionDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class DataSubmissionTest {

    @Autowired
    private DataSubmissionService dataSubmissionService;

    final String publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAtcEUFlnEZfDkPO/mxXwC\n" +
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
            "LyPpQIplL03J5EtG6FLHRWECAwEAAQ==";

    @Test
    void sendDataForRequest() {
        try {

            LocationDataRequestDto locationDataRequest = LocationDataRequestDto.builder().
                    keyOfHealthDepartment(publicKey).
                    keyReference("2470b56c-90b7-11eb-a8b3-0242ac130003").
                    submissionUri("https://localhost:8443/data-submissions/2470b56c-90b7-11eb-a8b3-0242ac130003/guests").
                    build();

            dataSubmissionService.sendDataForRequest(locationDataRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
