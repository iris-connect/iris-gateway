package iris.location_service.search.lucene;

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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LuceneIndexServiceTest {

    @Autowired
    private LuceneIndexService luceneIndexService;
    @Autowired
    private LuceneIndexServiceProperties luceneIndexServiceProperties;


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
        Location testObject = new Location(new LocationIdentifier("123","123"),"Pablo","Pablo Hun", "Pablo","Rom 1", "Rom", "12345","pablo.h@test.com","pablotest@test.de","01234 1512435");

        List<Location> testObjects = new ArrayList<>();
        testObjects.add(testObject);

        luceneIndexService.indexLocations(testObjects);
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
        Location loc = new Location(new LocationIdentifier("123","123"),"Pablo","Pablo Hun", "Pablo","Rom 1", "Rom", "12345","pablo.h@test.com","pablotest@test.de","01234 1512435");
        List<Location> locationList = new ArrayList<>();
        locationList.add(loc);
        int previous = luceneIndexService.search("Pablo").size();
        int previousCount = FSDirectory.open(Paths.get(luceneIndexServiceProperties.getIndexDirectory())).listAll().length;

        System.out.println("Vorheriger Wert: " + previous);
        luceneIndexService.deleteLocations(locationList);

        int expected = luceneIndexService.search("Pablo").size();
        int expectedCount = FSDirectory.open(Paths.get(luceneIndexServiceProperties.getIndexDirectory())).listAll().length;
        System.out.println("Vorheriger Wert: " + expected);


        assertTrue(previous > expected && previousCount == expectedCount-3);
    }

}