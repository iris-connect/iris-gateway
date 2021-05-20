package iris.location_service.search.db;


import iris.location_service.search.db.model.Location;
import iris.location_service.search.db.model.LocationIdentifier;
import iris.location_service.search.lucene.LocationAnalyzer;
import iris.location_service.search.lucene.LuceneIndexService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        LuceneIndexService controller = new LuceneIndexService() ;
        controller.setAnalyzer(analyzer);
        controller.setDir("target\\luceneTestIndex");
        controller.indexLocation(location);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(controller.getDir(), config);
        assertEquals(1,writer.getDocStats().maxDoc);
        writer.deleteAll();
        writer.close();
    }
}
