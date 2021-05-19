package iris.location_service.search.db;


import iris.location_service.search.db.model.Location;
import iris.location_service.search.db.model.LocationIdentifier;
import iris.location_service.search.lucene.LocationAnalyzer;
import iris.location_service.search.lucene.LuceneController;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LuceneAnalyzerTest {

    @Test
    public void writingIndexInDir() throws Exception {
        // When
        LocationIdentifier identifier = new LocationIdentifier("1","2");
        Location location = new Location(identifier,
                "Tobias","Hans"," nicht vorhanden","Hauptstraße","Salzgitter",
                "1234","tobias","test@web.de","12345");

        Analyzer analyzer = new LocationAnalyzer();
        LuceneController controller = new LuceneController() ;
        controller.setAnalyzer(analyzer);
        controller.setDir("target\\luceneTestIndex");
        controller.indexLocation(location);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(controller.getDir(), config);
        assertEquals(1,writer.getDocStats().maxDoc);
        writer.deleteAll();
        writer.close();
    }

    @Test
    public void test() throws Exception{
        Directory dir = FSDirectory.open(Paths.get("src\\main\\java\\iris\\location_service\\search\\lucene\\data"));
        Location testObject = new Location(new LocationIdentifier("123","123"),"Pablo","Pablo Hun", "Pablo","Rom 1", "Rom", "12345","pablo.h@test.com","pablotest@test.de","01234 1512435");
        LuceneController testIt = new LuceneController();
        int testExpected = dir.listAll().length+3;
        testIt.indexLocation(testObject);

        assertEquals(testExpected,dir.listAll().length);

    }
}
