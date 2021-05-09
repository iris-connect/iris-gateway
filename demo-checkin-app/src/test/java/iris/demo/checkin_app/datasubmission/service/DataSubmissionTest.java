package iris.demo.checkin_app.datasubmission.service;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.fail;

import iris.demo.checkin_app.IrisWireMockTest;
import iris.demo.checkin_app.datarequest.model.dto.LocationDataRequestDto;
import iris.demo.checkin_app.datasubmission.bootstrap.DataProviderLoader;
import iris.demo.checkin_app.datasubmission.bootstrap.GuestLoader;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@IrisWireMockTest
@Slf4j
class DataSubmissionTest {

	@Autowired
	private DataSubmissionService dataSubmissionService;
	@Autowired
	private GuestLoader guestLoader;
	@Autowired
	private DataProviderLoader dataProviderLoader;
	@Value("${wiremock.server.https-port}")
	private String port;

	@Test
	void sendDataForRequest() {

		String path = "/data-submissions/423576f4-9202-11eb-b5b7-00155da17da6/guests";
		var submissionUri = "https://localhost:" + port + path;

		stubFor(post(urlEqualTo(path)).willReturn(aResponse().withStatus(202)));

		try {

			LocationDataRequestDto locationDataRequest = LocationDataRequestDto.builder().start(Instant.now())
					.build();

			dataSubmissionService.sendDataForRequest(locationDataRequest);

		} catch (Exception e) {
			fail(e);
		}
	}

}
