package iris.demo.checkin_app.datasubmission.web.client;

import iris.demo.checkin_app.datasubmission.model.dto.GuestSubmissionDto;
import iris.demo.checkin_app.searchindex.model.LocationsDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class DataSubmissionClient {

    private final RestTemplate restTemplate;

    public DataSubmissionClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void postDataSubmissionGuests(GuestSubmissionDto guestSubmission, String submissionUrl) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        try {
            HttpEntity<GuestSubmissionDto> request =
                    new HttpEntity<GuestSubmissionDto>(guestSubmission, headers);

            String personResultAsJsonStr =
                    restTemplate.postForObject(submissionUrl, request, String.class);
        } catch (HttpClientErrorException e) {
            log.error("Request failed. Status code: " + e.getStatusCode().toString());
            log.error("Response: "+e.getResponseBodyAsString());
            throw new Exception("Request to Public API failed");
        }

    }
}
