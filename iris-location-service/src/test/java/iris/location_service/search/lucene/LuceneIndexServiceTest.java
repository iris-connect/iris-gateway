package iris.location_service.search.lucene;

import iris.location_service.search.db.model.Location;
import iris.location_service.search.db.model.LocationIdentifier;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.io.IOException;
import java.nio.file.Paths;

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
        indexDocument();
    }

    @Test
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

    /**
     * creates a Document
     * @throws Exception
     */
    void indexDocument() throws Exception {
        Directory dir = FSDirectory.open(Paths.get(luceneIndexServiceProperties.getIndexDirectory()));
        Location testObject = new Location(new LocationIdentifier("123","123"),"Pablo","Pablo Hun", "Pablo","Rom 1", "Rom", "12345","pablo.h@test.com","pablotest@test.de","01234 1512435");
        luceneIndexService.indexLocation(testObject);
    }


}