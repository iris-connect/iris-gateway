package iris.backend_service.locations.jsonrpc;

import static org.junit.jupiter.api.Assertions.*;

import iris.backend_service.core.validation.AttackDetector;
import iris.backend_service.jsonrpc.JsonRpcClientDto;
import iris.backend_service.locations.dto.LocationAddress;
import iris.backend_service.locations.dto.LocationContact;
import iris.backend_service.locations.dto.LocationInformation;
import iris.backend_service.locations.service.LocationService;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev_env")
class LocationRPCImplTest {

	String LOCATION_ID_1 = "ID_0001";
	String LOCATION_ID_2 = "ID_0002";

	@SpyBean
	LocationRPC systemUnderTest;

	@MockBean
	LocationService locationService;
	@MockBean
	AttackDetector attackDetector;

	@Test
	void postLocationsToSearchIndexWithCorrectData() {

		JsonRpcClientDto clientDto = getJsonClientDto();
		List<LocationInformation> locationList = getLocationInformationList();
		String returnString = systemUnderTest.postLocationsToSearchIndex(clientDto, locationList);

		assertEquals("OK", returnString);
	}

	@Test
	void postLocationsToSearchIndexWithIncorrectData() {

		JsonRpcClientDto clientDto = getJsonClientDto();
		List<LocationInformation> locationList = List.of(
				getLocationInformation(
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null));

		assertThrows(ConstraintViolationException.class,
				() -> systemUnderTest.postLocationsToSearchIndex(clientDto, locationList));
	}

	JsonRpcClientDto getJsonClientDto() {
		JsonRpcClientDto client = new JsonRpcClientDto();
		client.setName("testID");
		return client;
	}

	List<LocationInformation> getLocationInformationList() {

		return List.of(
				getLocationInformation(
						LOCATION_ID_1,
						"providerId",
						"name",
						"publicKey",
						"officialName",
						"representative",
						"ownerEmail@email.xx",
						"email@email.xx",
						"+4935112345678",
						"street",
						"city",
						"99999"),
				getLocationInformation(
						LOCATION_ID_2,
						"providerId",
						"name",
						"publicKey",
						"officialName",
						"representative",
						"ownerEmail@email.xx",
						"email@email.xx",
						"+4935112345678",
						"street",
						"city",
						"99999"));
	}

	LocationInformation getLocationInformation(
			String id,
			String providerId,
			String name,
			String publicKey,
			String officialName,
			String representative,
			String ownerEmail,
			String email,
			String phone,
			String street,
			String city,
			String zip) {
		LocationInformation location = new LocationInformation();
		location.setId(id);
		location.setName(name);
		location.setProviderId(providerId);
		location.setPublicKey(publicKey);

		LocationContact contact = new LocationContact();
		contact.setEmail(email);
		contact.setOfficialName(officialName);
		contact.setOwnerEmail(ownerEmail);
		contact.setPhone(phone);
		contact.setRepresentative(representative);

		LocationAddress address = new LocationAddress();
		address.setStreet(street);
		address.setCity(city);
		address.setZip(zip);
		contact.setAddress(address);

		location.setContact(contact);

		return location;
	}
}
