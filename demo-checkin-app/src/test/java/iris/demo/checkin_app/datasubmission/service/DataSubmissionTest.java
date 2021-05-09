package iris.demo.checkin_app.datasubmission.service;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

import iris.demo.checkin_app.IrisWireMockTest;
import iris.demo.checkin_app.datarequest.model.dto.LocationDataRequestDto;
import iris.demo.checkin_app.datasubmission.bootstrap.DataProviderLoader;
import iris.demo.checkin_app.datasubmission.bootstrap.GuestLoader;
import iris.demo.checkin_app.datasubmission.encryption.GuestListDecryptor;
import iris.demo.checkin_app.datasubmission.encryption.GuestListEncryptor;
import iris.demo.checkin_app.datasubmission.model.dto.DataProviderDto;
import iris.demo.checkin_app.datasubmission.model.dto.GuestDto;
import iris.demo.checkin_app.datasubmission.model.dto.GuestListDto;
import iris.demo.checkin_app.datasubmission.service.DataSubmissionService;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@IrisWireMockTest
@Slf4j
class DataSubmissionTest {

	final String publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAtcEUFlnEZfDkPO/mxXwC\n" +
			"NmNTjwlndnp4fk521W+lPqhQ5f8lipp6A2tnIhPeLtvwVN6q68hzASaWxbhAypp2\n" +
			"Bv77YRjoDacqx4gaq2eLGepb01CHNudGGvQGwhTYbfa8k13d2+z9+uN0/SrmofGc\n" +
			"ZvhjZWnxALGcdZRUn3Trk3O6uYrBODVk6HmpFMZKqL9tKtrCxLG17Yr9ek53sFJI\n" +
			"7YuEKxFbvF/20w5rcPYsmGgkoNjGhq2ajdGwe5UfcsGOEE/4tGScF2GMZM/Tjsh9\n" +
			"W9wISn6/e1v/Hhj9I19UfgasbQrE9lC1D01i1kTCjpYQ+dWcnA1Ulj2evymPpq9H\n" +
			"XVoKl8LmsfQ7n9w0vAfY2sPNW3H3wN/NlcuZslUTeTopZxtt4j7b7i+7Ik62t7Yr\n" +
			"CrioWQOlsWYME2YChzDCp6IlBOjeZtA9IDh6V3hbnDlV4AZygoMWWUWb1WS3oFNf\n" +
			"vaJfolxZRZXDUrsrQYpJPUZ8BexE0OPEdNNS8KCa9ANbhgO3xvSTSsPpbE7P346k\n" +
			"zyTCQxyJM66FLGu7vmGyt1sGiUBXFQCVYbSFNT3opke70f9/rYyzZoVA4ZBbAK7F\n" +
			"azMTNzUZzt9EICWupkdrDEcyuFQ3Q/9a8Bp14zAciIAujpWNMaeO9zi57W9V0Vd4\n" +
			"LyPpQIplL03J5EtG6FLHRWECAwEAAQ==";
	final String privateKey = "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\n" +
			"s/u9sTZGcpOwd3Zdsmd/Fg+4MR8WTQ==";
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

			LocationDataRequestDto locationDataRequest = LocationDataRequestDto.builder().keyOfHealthDepartment(publicKey)
					.keyReference("2470b56c-90b7-11eb-a8b3-0242ac130003").start(Instant.now())
					.build();

			dataSubmissionService.sendDataForRequest(locationDataRequest);

		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void testDecryption() {

		LocationDataRequestDto locationDataRequest = LocationDataRequestDto.builder().keyOfHealthDepartment(publicKey)
				.keyReference("2470b56c-90b7-11eb-a8b3-0242ac130003").build();

		List<GuestDto> guests = guestLoader.getGuests();
		DataProviderDto dataProvider = dataProviderLoader.getDataProvider();
		GuestListDto guestList = GuestListDto.builder().guests(guests).additionalInformation("").startDate(Instant.now())
				.endDate(Instant.now().plus(6, ChronoUnit.HOURS)).dataProvider(dataProvider).build();
		GuestListEncryptor encryptor = GuestListEncryptor.builder().guestList(guestList)
				.givenPublicKey(locationDataRequest.getKeyOfHealthDepartment()).build();

		String encryptedData = encryptor.encrypt();

		GuestListDecryptor decryptor = new GuestListDecryptor(encryptor.getSecretKeyBase64(), privateKey, encryptedData);

		log.info("json: " + guestList.asJson());
		assertEquals(guestList.asJson(), decryptor.decrypt());

	}
}
