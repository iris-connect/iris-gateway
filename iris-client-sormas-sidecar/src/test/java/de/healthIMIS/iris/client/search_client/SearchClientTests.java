package de.healthIMIS.iris.client.search_client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import de.healthIMIS.iris.client.IrisWireMockTest;
import lombok.RequiredArgsConstructor;
import wiremock.com.fasterxml.jackson.core.JsonProcessingException;
import wiremock.com.fasterxml.jackson.databind.JsonMappingException;
import de.healthIMIS.iris.api.sidecarclient.model.LocationInformation;
import de.healthIMIS.iris.api.sidecarclient.model.LocationList;
import de.healthIMIS.iris.client.data_request.Location;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@IrisWireMockTest
@RequiredArgsConstructor
public class SearchClientTests {
	
	private final SearchClient systemUnderTest;

	private final String SEARCH_PATH = "/search/";
	private final String SEARCH_KEY = "Test";
	private final String PROVIDER_ID = "providerId";
	private final String LOCATION_ID = "locationId";
	private final String NAME = "name";
	
	@Test
	public void searchByKeywordReturnsLocation() throws JsonMappingException, JsonProcessingException {		
		// given
		String searchPath = SEARCH_PATH + SEARCH_KEY;
		String json = "{\"locations\": [{\"id\": \"" + LOCATION_ID + "\", \"providerId\": \"" + PROVIDER_ID + "\", \"name\": \"" + NAME + "\"}]}";
		
		var locItem = new LocationInformation().id(LOCATION_ID).name("name").providerId(PROVIDER_ID);
		var locList = new LocationList().addLocationsItem(locItem);
		
		stubFor(get(urlEqualTo(searchPath)).willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(json)));

		// do
		LocationList searchResults = systemUnderTest.search(SEARCH_KEY);

		// then
		assertNotNull(searchResults);
		assertNotNull(searchResults.getLocations());
		assertEquals(searchResults.getLocations().size(), locList.getLocations().size());
		assertNotNull(searchResults.getLocations().get(0));
		assertEquals(searchResults.getLocations().get(0).getId(), locItem.getId());
		assertEquals(searchResults.getLocations().get(0).getName(), locItem.getName());
		assertEquals(searchResults.getLocations().get(0).getProviderId(), locItem.getProviderId());
	}
	
	@Test
	public void searchByIdReturnsLocation() throws JsonMappingException, JsonProcessingException {
		// given
		String searchPath = SEARCH_PATH + PROVIDER_ID + "/" + LOCATION_ID;
		String json = "{\"id\": \"" + LOCATION_ID + "\", \"providerId\": \"" + PROVIDER_ID + "\", \"name\": \"" + NAME + "\"}";
		
		var locItem = new LocationInformation().id(LOCATION_ID).name(NAME).providerId(PROVIDER_ID);

		stubFor(get(urlEqualTo(searchPath)).willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(json)));
		
		// do
		Location searchLocation = systemUnderTest.findByProviderIdAndLocationId(PROVIDER_ID, LOCATION_ID);
		
		// then
		assertNotNull(searchLocation);
		assertEquals(searchLocation.getLocationId(), locItem.getId());
		assertEquals(searchLocation.getProviderId(), locItem.getProviderId());
		assertEquals(searchLocation.getName(), locItem.getName());
	}
}

