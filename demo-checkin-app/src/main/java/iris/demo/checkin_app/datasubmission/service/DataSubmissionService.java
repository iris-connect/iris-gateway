package iris.demo.checkin_app.datasubmission.service;

import iris.demo.checkin_app.config.EPSClientProperties;
import iris.demo.checkin_app.datarequest.model.dto.LocationDataRequestDto;
import iris.demo.checkin_app.datasubmission.bootstrap.DataProviderLoader;
import iris.demo.checkin_app.datasubmission.bootstrap.GuestLoader;
import iris.demo.checkin_app.datasubmission.eps.EPSDataSubmissionClient;
import iris.demo.checkin_app.datasubmission.eps.dto.DataSubmissionDto;
import iris.demo.checkin_app.datasubmission.model.dto.DataProviderDto;
import iris.demo.checkin_app.datasubmission.model.dto.GuestDto;
import iris.demo.checkin_app.datasubmission.model.dto.GuestListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Component
@RequiredArgsConstructor
public class DataSubmissionService {

	private final @NotNull GuestLoader guestLoader;

	private final @NotNull DataProviderLoader dataProviderLoader;

	private final @NotNull EPSClientProperties epsClientProperties;

	private final @NotNull EPSDataSubmissionClient dataSubmissionClient;

	private final Random random = new Random();

	public String sendDataForRequest(LocationDataRequestDto locationDataRequest) throws Exception {

		List<GuestDto> guests;

		if ("a7184b09-f33e-4f0c-9986-cd031d0c0f41".equals(locationDataRequest.getLocationId())) {
			guests = guestLoader.getNaughtyGuests();
		} else if ("99999999-9999-9999-9999-999999999999".equals(locationDataRequest.getLocationId())) {
			throw new RuntimeException("Hier gab es einen Fehler ;)");
		} else {
			guests = guestLoader.getGuests();

			var start = Optional.ofNullable(locationDataRequest.getStart())
					.map(it -> it.minus(random.nextInt(100), ChronoUnit.MINUTES));
			var end = Optional.ofNullable(locationDataRequest.getEnd())
					.map(it -> it.plus(random.nextInt(100), ChronoUnit.MINUTES));
			// 2 of 3 should have plausible values
			for (int i = 0; i < 2; i++) {

				var guest = guests.get(i);
				guest.getAttendanceInformation().setAttendFrom(start.orElse(null));
				guest.getAttendanceInformation().setAttendTo(end.orElse(null));
			}
		}

		DataProviderDto dataProvider = dataProviderLoader.getDataProvider();

		GuestListDto guestList = GuestListDto.builder().guests(guests).additionalInformation("").startDate(Instant.now())
				.endDate(Instant.now().plus(6, ChronoUnit.HOURS)).dataProvider(dataProvider).build();

		DataSubmissionDto dataSubmissionDto = DataSubmissionDto.builder()
				.dataAuthorizationToken(locationDataRequest.getDataAuthorizationToken())
				.guestList(guestList).build();

		return dataSubmissionClient.postDataSubmissionGuests(dataSubmissionDto, locationDataRequest.getHdEndpoint());
	}
}
