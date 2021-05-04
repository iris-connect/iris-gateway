package iris.demo.checkin_app.datasubmission.service;

import iris.demo.checkin_app.datarequest.model.dto.LocationDataRequestDto;
import iris.demo.checkin_app.datasubmission.bootstrap.DataProviderLoader;
import iris.demo.checkin_app.datasubmission.bootstrap.GuestLoader;
import iris.demo.checkin_app.datasubmission.encryption.GuestListEncryptor;
import iris.demo.checkin_app.datasubmission.model.dto.DataProviderDto;
import iris.demo.checkin_app.datasubmission.model.dto.GuestDto;
import iris.demo.checkin_app.datasubmission.model.dto.GuestListDto;
import iris.demo.checkin_app.datasubmission.model.dto.GuestSubmissionDto;
import iris.demo.checkin_app.datasubmission.web.client.DataSubmissionClient;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Component
public class DataSubmissionService {

	@Autowired
	private GuestLoader guestLoader;

	@Autowired
	private DataProviderLoader dataProviderLoader;

	@Autowired
	private DataSubmissionClient dataSubmissionClient;

	private Random random = new Random();

	public void sendDataForRequest(LocationDataRequestDto locationDataRequest) throws Exception {

		List<GuestDto> guests = guestLoader.getGuests();

		var start = Optional.ofNullable(locationDataRequest.getStart())
				.map(it -> it.minus(random.nextInt(100), ChronoUnit.MINUTES));
		var end = Optional.ofNullable(locationDataRequest.getEnd())
				.map(it->it.plus(random.nextInt(100), ChronoUnit.MINUTES));
		// 2 of 3 should have plausible values
		for (int i = 0; i < 2; i++) {

			var guest = guests.get(i);
			guest.getAttendanceInformation().setAttendFrom(start.orElse(null));
			guest.getAttendanceInformation().setAttendTo(end.orElse(null));
		}

		DataProviderDto dataProvider = dataProviderLoader.getDataProvider();
    GuestListDto guestList = GuestListDto.builder().
            guests(guests).
            additionalInformation("").
            startDate(Instant.now()).
            endDate(Instant.now().plus(6, ChronoUnit.HOURS)).
            dataProvider(dataProvider).
            build();
    GuestListEncryptor encryptor = GuestListEncryptor.builder().
            guestList(guestList).
            givenPublicKey(locationDataRequest.getKeyOfHealthDepartment()).
            build();

    GuestSubmissionDto guestSubmission = GuestSubmissionDto.builder().
            encryptedData(encryptor.encrypt()).
            keyReference(locationDataRequest.getKeyReference()).
            secret(encryptor.getSecretKeyBase64()).
            build();

    dataSubmissionClient.postDataSubmissionGuests(guestSubmission, locationDataRequest.getSubmissionUri());
	}
}
