package iris.demo.checkin_app.datasubmission.bootstrap;

import static java.nio.charset.StandardCharsets.*;

import iris.demo.checkin_app.config.ResourceHelper;
import iris.demo.checkin_app.datasubmission.model.dto.AddressDto;
import iris.demo.checkin_app.datasubmission.model.dto.AttendanceInformationDto;
import iris.demo.checkin_app.datasubmission.model.dto.GuestDto;
import iris.demo.checkin_app.datasubmission.model.dto.SexDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RequiredArgsConstructor
@Component
public class GuestLoader {

	@NonNull
	private ResourceHelper resourceHelper;
	@NonNull
	private ObjectMapper objectMapper;

	@SneakyThrows
	public List<GuestDto> getGuests() {

		List<GuestDto> guests;

		var resource = resourceHelper.loadResource("classpath:bootstrap/guests/guest_list.json");

		guests = objectMapper.registerModule(new JavaTimeModule()).readValue(
				resource.getInputStream(), new TypeReference<>() {});

		return guests;
	}

	@SneakyThrows
	public List<GuestDto> getNaughtyGuests() {

		List<GuestDto> guests = new ArrayList<>();

		var resource = resourceHelper.loadResource("classpath:bootstrap/guests/naughty_people.txt");
		var strings = asString(resource).split("\n");

		for (var naughty_encoded : strings) {
			if (naughty_encoded.startsWith("#") || naughty_encoded.isBlank()) {
				continue;
			}
			System.out.println("Zeile: " + naughty_encoded);
			var naughty = new String(Base64.getDecoder().decode(naughty_encoded), UTF_8);
			var bad_guest = new GuestDto();
			bad_guest.setFirstName(naughty);
			bad_guest.setLastName(naughty);
			bad_guest.setDateOfBirth(Date.valueOf("1999-09-09"));
			bad_guest.setSex(SexDto.UNKNOWN);
			bad_guest.setEmail(naughty);
			bad_guest.setPhone(naughty);
			bad_guest.setMobilePhone(naughty);
			bad_guest.setAddress(new AddressDto(
					naughty,
					naughty,
					naughty,
					naughty));
			bad_guest.setAttendanceInformation(new AttendanceInformationDto(
					naughty,
					naughty,
					Instant.now(),
					Instant.now()));
			bad_guest.setIdentityChecked(true);
			guests.add(bad_guest);
		}

		return guests;
	}

	public static String asString(Resource resource) {
		try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
			return FileCopyUtils.copyToString(reader);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
