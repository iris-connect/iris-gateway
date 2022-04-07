package iris.demo.checkin_app.datasubmission.bootstrap;

import static org.junit.jupiter.api.Assertions.*;

import iris.demo.checkin_app.IrisWebIntegrationTest;
import iris.demo.checkin_app.datasubmission.model.dto.GuestDto;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@IrisWebIntegrationTest
class GuestLoaderTest {

	@Autowired
	private GuestLoader guestLoader;

	@Autowired
	private DataProviderLoader dataProviderLoader;

	@Test
	List<GuestDto> getGuests() {

		List<GuestDto> guests = guestLoader.getGuests();

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(guests);
		} catch (JsonProcessingException e) {
			fail(e);
		}
		return guests;
	}

}
