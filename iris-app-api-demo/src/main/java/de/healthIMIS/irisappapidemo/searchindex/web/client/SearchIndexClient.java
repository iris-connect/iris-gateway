package de.healthIMIS.irisappapidemo.searchindex.web.client;

import de.healthIMIS.irisappapidemo.searchindex.model.LocationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * Created by lucky-lusa on 2021-03-27.
 */
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

    public void updateLocations(List<LocationDto> locationsDto) {
        var headers = new HttpHeaders();
        headers.setContentType(new MediaType(APPLICATION_JSON, UTF_8));
        HttpEntity<List<LocationDto>> requestUpdate = new HttpEntity<>(locationsDto, headers);
        restTemplate.exchange(apihost + LOCATIONS_PATH, HttpMethod.PUT, requestUpdate, Void.class);
    }

    public void deleteLocation(LocationDto locationDto) {
        restTemplate.delete(apihost + LOCATIONS_PATH + '/' + locationDto.getId());
    }

    public void setApihost(String apihost) {
        this.apihost = apihost;
    }
}
