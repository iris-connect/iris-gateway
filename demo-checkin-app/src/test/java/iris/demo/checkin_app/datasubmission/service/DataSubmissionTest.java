package iris.demo.checkin_app.datasubmission.service;

import iris.demo.checkin_app.IrisWireMockTest;
import iris.demo.checkin_app.datasubmission.bootstrap.DataProviderLoader;
import iris.demo.checkin_app.datasubmission.bootstrap.GuestLoader;
import lombok.extern.slf4j.Slf4j;

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

	// @Test
	// void sendDataForRequest() {
	//
	// try {
	//
	// LocationDataRequestDto locationDataRequest = LocationDataRequestDto.builder().start(Instant.now())
	// .build();
	//
	// dataSubmissionService.sendDataForRequest(locationDataRequest);
	//
	// } catch (Exception e) {
	// fail(e);
	// }
	// }

}
