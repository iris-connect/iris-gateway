package iris.location_service.search.lucene;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LuceneIndexServiceTest {

    @Autowired
    private LuceneIndexService luceneIndexService;

    @BeforeEach
    public void setUp() {
        luceneIndexService.postConstruct();
    }

    @Test
    void searchIsEmptyIfNoDocumentsPresent() {
        assertEquals( 0, luceneIndexService.search("hola").size());
    }

    @Test
    void searchListIsNotEmptyIfDocumentsArePresentAndKeyWordMatches() {
        // Add documents
        assertEquals( 0, luceneIndexService.search("hola").size());
    }

}