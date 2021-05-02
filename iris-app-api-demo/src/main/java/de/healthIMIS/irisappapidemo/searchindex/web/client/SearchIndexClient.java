package de.healthIMIS.irisappapidemo.searchindex.web.client;

import de.healthIMIS.irisappapidemo.searchindex.model.LocationDto;
import de.healthIMIS.irisappapidemo.searchindex.model.LocationsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ConfigurationProperties(prefix = "iris.public-api", ignoreUnknownFields = false)
@Component
@Slf4j
public class SearchIndexClient {

    private final static String TEMPORARY_PROVIDER_ID = "f002f370-bd54-4325-ad91-1aff3bf730a5";

    public final String LOCATIONS_PATH = "/search-index/locations";
    private final RestTemplate restTemplate;
    private String apihost;

    public SearchIndexClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void updateLocations(LocationsDto locationsDto) {
        var headers = new HttpHeaders();
        headers.add("x-provider-id", TEMPORARY_PROVIDER_ID);
        HttpEntity<LocationsDto> requestUpdate = new HttpEntity<LocationsDto>(locationsDto, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(apihost + LOCATIONS_PATH, HttpMethod.PUT, requestUpdate, String.class);
            log.info(response.toString());
        } catch (HttpClientErrorException e) {
            log.error("Request failed. Status code: " + e.getStatusCode().toString());
            log.error("Response: "+e.getResponseBodyAsString());
        }

    }

    public void deleteLocation(LocationDto locationDto) {
        var headers = new HttpHeaders();
        headers.add("x-provider-id", TEMPORARY_PROVIDER_ID);
        HttpEntity<?> requestDelete = new HttpEntity<LocationsDto>(null, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(apihost + LOCATIONS_PATH + '/' + locationDto.getId(), HttpMethod.DELETE, requestDelete, String.class);
            log.info(response.toString());
        } catch (HttpClientErrorException e) {
            log.error("Request failed. Status code: " + e.getStatusCode().toString());
        }
    }

    public void setApihost(String apihost) {
        this.apihost = apihost;
    }
}
