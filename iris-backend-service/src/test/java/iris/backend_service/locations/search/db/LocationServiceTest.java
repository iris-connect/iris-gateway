package iris.backend_service.locations.search.db;

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
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev_env")
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
	String VALID_PHONE2 = "+49 35204 789 33";
	String INVALID_PHONE = "0123A56789";

	String REPRESENTATIVE_NAME = "Cliff Hanger";

	String LOCATION_NAME = "Testsite Alpha";

	LocationService systemUnderTest;

	@Autowired
	ModelMapper mapper;

	@Mock
	LocationRepository locationRepo;

	@Mock
	DBSearchIndex index;

	@BeforeEach
	void setUp() {
		systemUnderTest = new LocationService(mapper, locationRepo, index);
	}

	@Test
	public void addLocationsWithValidData() {
		JsonRpcClientDto clientDto = getJsonClientDto();
		List<LocationInformation> locationList = getLocationInformationListWithOnlyValidCases();

		systemUnderTest.addLocations(clientDto.getName(), locationList);

		verify(locationRepo).saveAll(anyList());
	}

	JsonRpcClientDto getJsonClientDto() {
		JsonRpcClientDto client = new JsonRpcClientDto();
		client.setName("testID");
		return client;
	}

	List<LocationInformation> getLocationInformationList() {
		List<LocationInformation> locationList = new ArrayList<>();
		locationList.add(
				getLocationInformation(
						LOCATION_ID_1,
						"providerId",
						LOCATION_NAME,
						"publicKey",
						"officialName",
						REPRESENTATIVE_NAME,
						"ownerEmail",
						VALID_EMAIL,
						VALID_PHONE));
		locationList.add(
				getLocationInformation(
						LOCATION_ID_2,
						"providerId",
						null,
						"publicKey",
						"officialName",
						REPRESENTATIVE_NAME,
						"ownerEmail",
						VALID_EMAIL,
						VALID_PHONE));
		locationList.add(
				getLocationInformation(
						LOCATION_ID_3,
						"providerId",
						LOCATION_NAME,
						"publicKey",
						"officialName",
						null,
						"ownerEmail",
						VALID_EMAIL,
						VALID_PHONE));
		locationList.add(
				getLocationInformation(
						LOCATION_ID_4,
						"providerId",
						LOCATION_NAME,
						"publicKey",
						"officialName",
						REPRESENTATIVE_NAME,
						"ownerEmail",
						null,
						null));
		locationList.add(
				getLocationInformation(
						LOCATION_ID_5,
						"providerId",
						LOCATION_NAME,
						"publicKey",
						"officialName",
						REPRESENTATIVE_NAME,
						"ownerEmail",
						null,
						VALID_PHONE));
		locationList.add(
				getLocationInformation(
						LOCATION_ID_6,
						"providerId",
						LOCATION_NAME,
						"publicKey",
						"officialName",
						REPRESENTATIVE_NAME,
						"ownerEmail",
						INVALID_EMAIL,
						null));
		locationList.add(
				getLocationInformation(
						LOCATION_ID_7,
						"providerId",
						LOCATION_NAME,
						"publicKey",
						"officialName",
						REPRESENTATIVE_NAME,
						"ownerEmail",
						VALID_EMAIL,
						null));
		locationList.add(
				getLocationInformation(
						LOCATION_ID_8,
						"providerId",
						LOCATION_NAME,
						"publicKey",
						"officialName",
						REPRESENTATIVE_NAME,
						"ownerEmail",
						null,
						INVALID_PHONE));
		return locationList;
	}

	List<LocationInformation> getLocationInformationListWithOnlyValidCases() {
		List<LocationInformation> locationList = new ArrayList<>();
		locationList.add(
				getLocationInformation(
						LOCATION_ID_1,
						"providerId",
						LOCATION_NAME,
						"publicKey",
						"officialName",
						REPRESENTATIVE_NAME,
						"ownerEmail",
						VALID_EMAIL,
						VALID_PHONE));
		locationList.add(
				getLocationInformation(
						LOCATION_ID_5,
						"providerId",
						LOCATION_NAME,
						"publicKey",
						"officialName",
						REPRESENTATIVE_NAME,
						"ownerEmail",
						null,
						VALID_PHONE));
		locationList.add(
				getLocationInformation(
						LOCATION_ID_7,
						"providerId",
						LOCATION_NAME,
						"publicKey",
						"officialName",
						REPRESENTATIVE_NAME,
						"ownerEmail",
						VALID_EMAIL,
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
