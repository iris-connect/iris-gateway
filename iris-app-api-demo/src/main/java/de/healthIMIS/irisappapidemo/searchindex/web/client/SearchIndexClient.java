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

    public final String LOCATIONS_PATH = "/search-index/locations";
    private final RestTemplate restTemplate;
    private String apihost;

    public SearchIndexClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void updateLocations(LocationsDto locationsDto) {
        var headers = new HttpHeaders();
        //headers.setContentType(new MediaType(APPLICATION_JSON));
        //HttpEntity<LocationsDto> requestUpdate = new HttpEntity<LocationsDto>(locationsDto, headers);
        try {
            restTemplate.put(apihost + LOCATIONS_PATH, locationsDto);
        } catch (HttpClientErrorException e) {
            log.error("Request failed. Status code: " + e.getStatusCode().toString());
            log.error("Response: "+e.getResponseBodyAsString());
        }

    }

    public void deleteLocation(LocationDto locationDto) {
        try {
            restTemplate.delete(apihost + LOCATIONS_PATH + '/' + locationDto.getId());
        } catch (HttpClientErrorException e) {
            log.error("Request failed. Status code: " + e.getStatusCode().toString());
        }
    }

    public void setApihost(String apihost) {
        this.apihost = apihost;
    }
}
