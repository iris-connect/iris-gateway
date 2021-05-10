package iris.demo.checkin_app.searchindex.web.client;

import iris.demo.checkin_app.IrisWireMockTest;
import iris.demo.checkin_app.searchindex.bootstrap.LocationsLoader;
import iris.demo.checkin_app.searchindex.eps.EPSSearchIndexClient;

import org.springframework.beans.factory.annotation.Autowired;

@IrisWireMockTest
class SearchIndexClientTest {

	@Autowired
	EPSSearchIndexClient client;

	@Autowired
	LocationsLoader locationsLoader;

	public final String LOCATIONS_PATH = "/search-index/locations";

	// @Test
	// void testUpdateLocations() throws IRISDataSubmissionException {
	// stubFor(put(urlEqualTo(LOCATIONS_PATH)).willReturn(aResponse().withStatus(204)));
	//
	// stubFor(delete(urlEqualTo(LOCATIONS_PATH)).willReturn(aResponse().withStatus(200)));
	//
	// LocationsDto locations = locationsLoader.getDemoLocations();
	//
	// client.updateLocations(locations);
	// }
	//
	// @Test
	// void testDeleteLocation() throws IRISDataSubmissionException {
	// stubFor(put(urlEqualTo(LOCATIONS_PATH + "/0d4b783a-59fa-4687-9bf8-d1f7e15d040d"))
	// .willReturn(aResponse().withStatus(204)));
	//
	// stubFor(delete(urlEqualTo(LOCATIONS_PATH + "/0d4b783a-59fa-4687-9bf8-d1f7e15d040d"))
	// .willReturn(aResponse().withStatus(200)));
	//
	// LocationsDto locations = locationsLoader.getDemoLocations();
	//
	// client.deleteLocation(locations.getLocations().get(1));
	// }
}
