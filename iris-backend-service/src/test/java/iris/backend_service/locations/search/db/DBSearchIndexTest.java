package iris.backend_service.locations.search.db;

import static org.junit.jupiter.api.Assertions.*;

import iris.backend_service.locations.dto.LocationAddress;
import iris.backend_service.locations.dto.LocationContact;
import iris.backend_service.locations.dto.LocationInformation;
import iris.backend_service.locations.dto.LocationQueryResult;
import iris.backend_service.locations.search.db.model.Location;
import iris.backend_service.locations.search.db.model.LocationIdentifier;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("dev_env")
class DBSearchIndexTest {

	@Autowired
	private DBSearchIndex searchIndex;

	@Autowired
	private LocationRepository locationRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ObjectMapper oMapper;

	@Test
	public void search() {
		// When
		var location = createLocation();

		// then
		locationRepo.saveAll(Collections.singletonList(location));

		// assert
		assertEquals(1, searchIndex.search("Zwoeinz", PageRequest.of(0, 20)).getContent().size());
		assertEquals(1, searchIndex.search("zwoeinz", PageRequest.of(0, 20)).getContent().size());
	}

	@Test
	public void map() throws JsonProcessingException {
		var in = "{\n" + "  \"locations\": [\n" + "    {\n" + "      \"id\": \"5eddd61036d39a0ff8b11fdb\",\n"
				+ "      \"name\": \"Restaurant Alberts\",\n" + "      \"publicKey\": \"string\",\n" + "      \"contact\": {\n"
				+ "        \"officialName\": \"Darfichrein GmbH\",\n" + "        \"representative\": \"Silke \",\n"
				+ "        \"address\": {\n"
				+ "          \"street\": \"Türkenstr. 7\",\n" + "          \"city\": \"München\",\n"
				+ "          \"zip\": \"80333\"\n" + "        },\n"
				+ "        \"ownerEmail\": \"covid@restaurant.de\",\n" + "        \"email\": \"covid2@restaurant.de\",\n"
				+ "        \"phone\": \"die bleibt privat :-)\"\n" + "      },\n" + "      \"contexts\": [\n" + "        {\n"
				+ "          \"id\": \"5f4bfff742c1bf5f72918851\",\n" + "          \"name\": \"Raum 0815\"\n" + "        }\n"
				+ "      ]\n" + "    }\n"
				+ "  ],\n" + " \"totalElements\": 3 }";
		var ll = oMapper.readValue(in, LocationQueryResult.class);
		assertEquals(1, ll.getLocations().size());
		assertEquals(3, ll.getTotalElements());

		var location = ll.getLocations().get(0);
		assertEquals("covid@restaurant.de", location.getContact().getOwnerEmail());
	}

	@Test
	public void delete() {

	}

	private Location createLocation() {
		var address = new LocationAddress();
		address.setCity("Berlin");
		address.setStreet("Quitzowstraße 121");
		address.setZip("15434");

		var contact = new LocationContact();
		contact.setOwnerEmail("test@mail.com");
		contact.setEmail("test2@mail.com");
		contact.setPhone("01234567890");
		contact.setOfficialName("Foo Bar GmbH");
		contact.setAddress(address);

		var location = new LocationInformation();
		location.setName("Zwoeinz Bar");
		location.setId("ac04638c-9668-4a62-8194-d20833e6182f");
		location.setContact(contact);

		var location_flat = mapper.map(location, Location.class);
		location_flat.setId(new LocationIdentifier("provider-id", location.getId()));
		return location_flat;
	}

}
