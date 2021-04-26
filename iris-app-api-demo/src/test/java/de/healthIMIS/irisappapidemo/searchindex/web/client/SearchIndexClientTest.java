package de.healthIMIS.irisappapidemo.searchindex.web.client;

import de.healthIMIS.irisappapidemo.searchindex.bootstrap.LocationsLoader;
import de.healthIMIS.irisappapidemo.searchindex.model.*;
import de.healthIMIS.irisappapidemo.WireMockContextInitializer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {WireMockContextInitializer.class})
@ActiveProfiles("test")
class SearchIndexClientTest {

    @Autowired
    SearchIndexClient client;

    @Autowired
    LocationsLoader locationsLoader;
    
    public final String LOCATIONS_PATH = "/search-index/locations";
        
    @Autowired
	WireMockServer wireMockServer;
	
    @AfterEach
    void shutDown() {	
		this.wireMockServer.resetAll();
    }

    @Test
    void testUpdateLocations() {
    	stubFor(put(urlEqualTo(LOCATIONS_PATH)).willReturn(aResponse().withStatus(204)));

    	stubFor(delete(urlEqualTo(LOCATIONS_PATH)).willReturn(aResponse().withStatus(200)));
    	
        LocationsDto locations = locationsLoader.getDemoLocations();

        client.updateLocations(locations);
    }

    @Test
    void testDeleteLocation() {
    	stubFor(put(urlEqualTo(LOCATIONS_PATH)).willReturn(aResponse().withStatus(204)));

    	stubFor(delete(urlEqualTo(LOCATIONS_PATH)).willReturn(aResponse().withStatus(200)));

        LocationsDto locations = locationsLoader.getDemoLocations();

        client.deleteLocation(locations.getLocations().get(1)); 
    }
}