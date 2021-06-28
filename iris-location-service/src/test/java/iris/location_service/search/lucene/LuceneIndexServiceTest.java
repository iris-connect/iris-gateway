package iris.location_service.search.lucene;

import iris.location_service.dto.LocationAddress;
import iris.location_service.dto.LocationContact;
import iris.location_service.dto.LocationInformation;
import iris.location_service.search.db.model.Location;
import iris.location_service.search.db.model.LocationIdentifier;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import javax.annotation.PostConstruct;
import javax.persistence.PostLoad;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LuceneIndexServiceTest {

    @Autowired
    private LuceneIndexService luceneIndexService;
    @Autowired
    private LuceneIndexServiceProperties luceneIndexServiceProperties;

    private UUID providerId =  UUID.fromString("f002f370-bd54-4325-ad91-1aff3bf730a5");

    @BeforeEach
    public void setUp() throws Exception {
        luceneIndexService.postConstruct();
        System.out.println("Test start");
    }

    /**
     * creates a Document
     * @throws Exception
     */
    @BeforeEach
    void indexDocument() throws Exception {
        Directory dir = FSDirectory.open(Paths.get(luceneIndexServiceProperties.getIndexDirectory()));
        Location testLocation = new Location(new LocationIdentifier("f002f370-bd54-4325-ad91-1aff3bf730a5","123"),"Pablo","Pablo Hun", "Pablo","Rom 1", "Rom", "12345","pablo.h@test.com","pablotest@test.de","01234 1512435");

        List<LocationInformation> list = getNewLocationInformation(testLocation);



        luceneIndexService.indexLocations(providerId, list);
    }

    private List<LocationInformation> getNewLocationInformation(Location testLocation) {
        LocationInformation locInf = new LocationInformation();

        LocationContact locationContact = new LocationContact();
        LocationAddress locationAddress = new LocationAddress();
        locationAddress.setStreet(testLocation.getContactAddressStreet());
        locationAddress.setCity(testLocation.getContactAddressCity());
        locationAddress.setZip(testLocation.getContactAddressZip());

        locationContact.setAddress(locationAddress);
        locationContact.setEmail(testLocation.getContactEmail());
        locationContact.setOfficialName(testLocation.getContactOfficialName());
        locationContact.setOwnerEmail(testLocation.getContactOwnerEmail());
        locationContact.setPhone(testLocation.getContactPhone());
        locationContact.setRepresentative(testLocation.getContactRepresentative());

        locInf.setId(testLocation.getId().getLocationId());
        locInf.setProviderId( testLocation.getId().getProviderId());
        locInf.setName(testLocation.getName());
        locInf.setContact(locationContact);

        List<LocationInformation> locList = new ArrayList<>();
        locList.add(locInf);

        return locList;
    }

    //TODO an den neuen Testdurchlauf anpassen
    @Test
    @Disabled
    public void testWriter() throws Exception{
        int testExpected =  FSDirectory.open(Paths.get(luceneIndexServiceProperties.getIndexDirectory())).listAll().length;
        int testValue = FSDirectory.open(Paths.get(luceneIndexServiceProperties.getIndexDirectory())).listAll().length;
        assertTrue(testExpected<=testValue);
    }

    /**
     * Testing if the search for vwxyz is empty
     */
    @Test
    void searchIsEmptyIfNoDocumentsPresent() {
        assertEquals( 0, luceneIndexService.search("vwxyz").size());
    }

    /**
     * Testing if the search for the previous indexed location is defined
     */
    @Test
    void searchListIsNotEmptyIfDocumentsArePresentAndKeyWordMatches() throws Exception {
        assertTrue( 1<= luceneIndexService.search("pablo").size());
    }

    @Test
    void deleteLocation() throws IOException {
        // Location loc = new Location(new LocationIdentifier("f002f370-bd54-4325-ad91-1aff3bf730a5","123"),"Pablo","Pablo Hun", "Pablo","Rom 1", "Rom", "12345","pablo.h@test.com","pablotest@test.de","01234 1512435");
        int previous = luceneIndexService.search("Pablo").size();
        int previousCount = FSDirectory.open(Paths.get(luceneIndexServiceProperties.getIndexDirectory())).listAll().length;

        System.out.println("Anzahl gefundener Doc vor dem Löschen: " + previous);
        luceneIndexService.deleteLocation(providerId, "123");

        int expected = luceneIndexService.search("Pablo").size();
        int expectedCount = FSDirectory.open(Paths.get(luceneIndexServiceProperties.getIndexDirectory())).listAll().length;
        System.out.println("Anzahl gefundener Doc nach dem Löschen: " + expected);

        assertTrue(previous > expected && previousCount == expectedCount+3);
    }

    @Test
    void checkIfLocationInformationUpdatesWhenSameID() throws IOException {
        int sizeBeforeUpdate = luceneIndexService.search("Pablo").size();
        assertEquals(1, sizeBeforeUpdate);

        Location testLocation = new Location(new LocationIdentifier("f002f370-bd54-4325-ad91-1aff3bf730a5","123"),"Escobar","Escobar Test", "Escobar","Rom 1", "Rom", "12345","Escobar.h@test.com","Escobartest@test.de","01234 1512435");
        List<LocationInformation> list = getNewLocationInformation(testLocation);
        luceneIndexService.indexLocations(providerId, list);

        int sizeAfterUpdate = luceneIndexService.search("Pablo").size();
        assertEquals(0, sizeAfterUpdate);

        int sizeAfterUpdate2 = luceneIndexService.search("Escobar").size();
        assertEquals(1, sizeAfterUpdate2);
    }

    @Test
    void checkIfLocationIdentifierIsUnique() throws IOException {
        long locationCount = luceneIndexService.searchByProviderIdAndLocationId("f002f370-bd54-4325-ad91-1aff3bf730a5", "123").stream().count();
        assertEquals(1, locationCount);

        Location testLocation = new Location(new LocationIdentifier("f002f370-bd54-4325-ad91-1aff3bf730a5","123"),"Escobar","Escobar Test", "Escobar","Rom 1", "Rom", "12345","Escobar.h@test.com","Escobartest@test.de","01234 1512435");
        List<LocationInformation> list = getNewLocationInformation(testLocation);
        luceneIndexService.indexLocations(providerId, list);

        long locationCount2 = luceneIndexService.searchByProviderIdAndLocationId("f002f370-bd54-4325-ad91-1aff3bf730a5", "123").stream().count();
        assertEquals(1, locationCount2);
    }

}