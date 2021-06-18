package iris.location_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import iris.location_service.dto.LocationContact;
import iris.location_service.dto.LocationInformation;
import iris.location_service.jsonrpc.JsonRpcClientDto;
import iris.location_service.search.db.DBSearchIndex;
import iris.location_service.search.db.LocationDAO;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

	String LOCATION_ID_1 = "ID_0001_Valid";
	String LOCATION_ID_2 = "ID_0002_Name_Null";
	String LOCATION_ID_3 = "ID_0003_Representative_Null";
	String LOCATION_ID_4 = "ID_0004_Phone_and_Email_Null";
	String LOCATION_ID_5 = "ID_0005_Email_Null_and_Phone_Valid";
	String LOCATION_ID_6 = "ID_0006_Email_Invalid_and_Phone_Null";
	String LOCATION_ID_7 = "ID_0007_Phone_Null_and_Email_Valid";
	String LOCATION_ID_8 = "ID_0008_Phone_Invalid_and_Email_Null";

	String VALID_EMAIL = "john.doe@gmx.de";
	String INVALID_EMAIL = "jane.doe.gmx.de";

	String VALID_PHONE = "0123456789";
	String INVALID_PHONE = "0123A56789";

	String REPRESENTATIVE_NAME = "Cliff Hanger";

	String LOCATION_NAME = "Testsite Alpha";

	LocationService systemUnderTest;

	@Mock
	ModelMapper mapper;

	@Mock
	LocationDAO locationDao;

	@Mock
	DBSearchIndex index;

	@BeforeEach
	void setUp() {
		systemUnderTest = new LocationService(mapper, locationDao, index);
	}

	@Test
	public void addLocationsWithValidData() {
		JsonRpcClientDto clientDto = getJsonClientDto();
		List<LocationInformation> locationList = getLocationInformationListWithOnlyValidCases();

		// TODO: WHEN cases

		List<String> listOfReturnStrings = systemUnderTest.addLocations(clientDto.getName(), locationList);

		assertEquals(0, listOfReturnStrings.size());
	}

	@Test
	public void addLocationsWithInvalidData() {
		JsonRpcClientDto clientDto = getJsonClientDto();
		List<LocationInformation> locationList = getLocationInformationList();

		// TODO: WHEN cases		

		List<String> listOfReturnStrings = systemUnderTest.addLocations(clientDto.getName(), locationList);

		assertEquals(5, listOfReturnStrings.size());
		assertEquals(LOCATION_ID_2, listOfReturnStrings.get(1));
		assertEquals(LOCATION_ID_3, listOfReturnStrings.get(2));
		assertEquals(LOCATION_ID_4, listOfReturnStrings.get(3));
		assertEquals(LOCATION_ID_6, listOfReturnStrings.get(6));
		assertEquals(LOCATION_ID_8, listOfReturnStrings.get(7));
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
				"LOCATION_NAME",
				"publicKey",
				"officialName",
				"REPRESENTATIVE_NAME",
				"ownerEmail",
				"VALID_EMAIL",
				"VALID_PHONE"));
		locationList.add(
			getLocationInformation(
				LOCATION_ID_2,
				"providerId",
				null,
				"publicKey",
				"officialName",
				"REPRESENTATIVE_NAME",
				"ownerEmail",
				"VALID_EMAIL",
				"VALID_PHONE"));
		locationList.add(
			getLocationInformation(
				LOCATION_ID_3,
				"providerId",
				"LOCATION_NAME",
				"publicKey",
				"officialName",
				null,
				"ownerEmail",
				"VALID_EMAIL",
				"VALID_PHONE"));
		locationList.add(
			getLocationInformation(
				LOCATION_ID_4,
				"providerId",
				"LOCATION_NAME",
				"publicKey",
				"officialName",
				"REPRESENTATIVE_NAME",
				"ownerEmail",
				null,
				null));
		locationList.add(
			getLocationInformation(
				LOCATION_ID_5,
				"providerId",
				"LOCATION_NAME",
				"publicKey",
				"officialName",
				"REPRESENTATIVE_NAME",
				"ownerEmail",
				null,
				"VALID_PHONE"));
		locationList.add(
			getLocationInformation(
				LOCATION_ID_6,
				"providerId",
				"LOCATION_NAME",
				"publicKey",
				"officialName",
				"REPRESENTATIVE_NAME",
				"ownerEmail",
				"INVALID_EMAIL",
				null));
		locationList.add(
			getLocationInformation(
				LOCATION_ID_7,
				"providerId",
				"LOCATION_NAME",
				"publicKey",
				"officialName",
				"REPRESENTATIVE_NAME",
				"ownerEmail",
				"VALID_EMAIL",
				null));
		locationList.add(
			getLocationInformation(
				LOCATION_ID_8,
				"providerId",
				"LOCATION_NAME",
				"publicKey",
				"officialName",
				"REPRESENTATIVE_NAME",
				"ownerEmail",
				null,
				"INVALID_PHONE"));
		return locationList;
	}

	List<LocationInformation> getLocationInformationListWithOnlyValidCases() {
		List<LocationInformation> locationList = new ArrayList<LocationInformation>();
		locationList.add(
			getLocationInformation(
				LOCATION_ID_1,
				"providerId",
				"LOCATION_NAME",
				"publicKey",
				"officialName",
				"REPRESENTATIVE_NAME",
				"ownerEmail",
				"VALID_EMAIL",
				"VALID_PHONE"));
		locationList.add(
			getLocationInformation(
				LOCATION_ID_5,
				"providerId",
				"LOCATION_NAME",
				"publicKey",
				"officialName",
				"REPRESENTATIVE_NAME",
				"ownerEmail",
				null,
				"VALID_PHONE"));
		locationList.add(
			getLocationInformation(
				LOCATION_ID_7,
				"providerId",
				"LOCATION_NAME",
				"publicKey",
				"officialName",
				"REPRESENTATIVE_NAME",
				"ownerEmail",
				"VALID_EMAIL",
				null));
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
