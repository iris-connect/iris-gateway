package de.healthIMIS.irisappapidemo;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.junit.jupiter.api.Test;

@IrisWireMockTest
class IrisAppApiDemoApplicationTests {

	public final String LOCATIONS_PATH = "/search-index/locations";

	@Test
	void contextLoads() {
		stubFor(put(urlEqualTo(LOCATIONS_PATH)).willReturn(aResponse().withStatus(204)));

		stubFor(delete(urlEqualTo(LOCATIONS_PATH)).willReturn(aResponse().withStatus(200)));
	}
}
