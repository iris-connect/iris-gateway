package iris.backend_service.locations.jsonrpc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import iris.backend_service.jsonrpc.JsonRpcClientDto;
import iris.backend_service.locations.dto.LocationContact;
import iris.backend_service.locations.dto.LocationInformation;
import iris.backend_service.locations.service.LocationService;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LocationRPCImplTest {

	String LOCATION_ID_1 = "ID_0001";
	String LOCATION_ID_2 = "ID_0002";

	LocationRPCImpl systemUnderTest;

	@Mock
	LocationService locationService;

	@BeforeEach
	void setUp() {
		systemUnderTest = new LocationRPCImpl(locationService);
	}

	@Test
	public void postLocationsToSearchIndexWithCorrectData() {
		List<String> returnList = new ArrayList<String>();

		when(locationService.addLocations(any(), any())).thenReturn(returnList);

		JsonRpcClientDto clientDto = getJsonClientDto();
		List<LocationInformation> locationList = getLocationInformationList();
		String returnString = systemUnderTest.postLocationsToSearchIndex(clientDto, locationList);

		assertEquals("OK", returnString);
	}

	@Test
	public void postLocationsToSearchIndexWithIncorrectData() {
		List<String> returnList = new ArrayList<String>();
		returnList.add(LOCATION_ID_1);
		returnList.add(LOCATION_ID_2);

		when(locationService.addLocations(any(), any())).thenReturn(returnList);

		JsonRpcClientDto clientDto = getJsonClientDto();
		List<LocationInformation> locationList = getLocationInformationList();
		String returnString = systemUnderTest.postLocationsToSearchIndex(clientDto, locationList);

		String expectedReturnString = "Invalid Locations detected: " + LOCATION_ID_1 + ", " + LOCATION_ID_2;
		assertEquals(expectedReturnString, returnString);
	}

	JsonRpcClientDto getJsonClientDto() {
		JsonRpcClientDto client = new JsonRpcClientDto();
		client.setName("testID");
		return client;
	}

	List<LocationInformation> getLocationInformationList() {
		List<LocationInformation> locationList = new ArrayList<LocationInformation>();
		locationList.add(
				getLocationInformation(
						LOCATION_ID_1,
						"providerId",
						"name",
						"publicKey",
						"officialName",
						"representative",
						"ownerEmail",
						"email",
						"phone"));
		locationList.add(
				getLocationInformation(
						LOCATION_ID_2,
						"providerId",
						"name",
						"publicKey",
						"officialName",
						"representative",
						"ownerEmail",
						"email",
						"phone"));
		return locationList;
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
			String phone) {
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
		location.setContact(contact);
		return location;
	}

}
