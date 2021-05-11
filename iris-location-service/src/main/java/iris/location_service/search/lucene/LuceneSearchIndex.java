package iris.location_service.search.lucene;

import io.swagger.v3.oas.models.info.Contact;
import iris.location_service.dto.LocationInformation;
import iris.location_service.search.SearchIndex;
import iris.location_service.search.db.model.Location;
import lombok.AllArgsConstructor;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LuceneSearchIndex implements SearchIndex {

    private LuceneController luceneController;

    @Override
    public List<LocationInformation> search(String keyword) {
        try {
            // create lucene index searchr
            IndexReader reader = DirectoryReader.open(luceneController.getDir());
            IndexSearcher searcher = new IndexSearcher(reader);

            // create query
            QueryParser parser = new QueryParser("Name",luceneController.getAnalyzer()); // TODO: 09.05.2021 search through all fields
            Query query = parser.parse(keyword);

            // search
            TopDocs results = searcher.search(query, 10);

            // parse to location list
            List<LocationInformation> locationList = new ArrayList<>();
            for (ScoreDoc resultScore: results.scoreDocs) {
                Document result = searcher.doc(resultScore.doc);
                LocationInformation location = createLocationInformation(result);
                locationList.add(location);
            }
            return locationList;
        } catch (IOException | ParseException e) {
            return null; // TODO: 09.05.2021 return proper error object
        }
    }

    public LocationInformation createLocationInformation(Document document) {
        return new LocationInformation(); // TODO: 09.05.2021 add 'document to location information' parser
    }
}
