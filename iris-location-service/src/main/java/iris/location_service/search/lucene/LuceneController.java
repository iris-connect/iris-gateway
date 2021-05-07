package iris.location_service.search.lucene;

import iris.location_service.search.db.model.Location;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.MMapDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;


public class LuceneController {
    private StandardAnalyzer analyzer;
    private Directory dir;

    public LuceneController() throws IOException {
        analyzer = new StandardAnalyzer();
        dir = FSDirectory.open(Paths.get("iris-location-service\\src\\main\\java\\iris\\location_service\\search\\lucene\\data"));
    }

    public List<Location> search(){
        //ToDo implements search
        return null;
    }

    public void indexLocation(Location location) throws Exception {
        indexDocument(createDocument(location));
    }

    public void indexLocations(List<Location> locations) throws Exception {
        for(Location location:locations){
            indexDocument(createDocument(location));
        }
    }

    private Document createDocument(Location location){
        Document doc = new Document();
        // Is the ID relevant for us?
        // doc.add(new TextField("name", location.getId().toString(), Field.Store.YES));
        // "publicKey" is not gettable
        doc.add(new TextField("name", location.getName(), Field.Store.YES));
        doc.add(new TextField("name", location.getContactOfficialName(), Field.Store.YES));
        doc.add(new TextField("name", location.getContactRepresentative(), Field.Store.YES));
        doc.add(new TextField("name", location.getContactAddressStreet(), Field.Store.YES));
        doc.add(new TextField("name", location.getContactAddressCity(), Field.Store.YES));
        doc.add(new TextField("name", location.getContactAddressZip(), Field.Store.YES));
        doc.add(new TextField("name", location.getContactOwnerEmail(), Field.Store.YES));
        doc.add(new TextField("name", location.getContactEmail(), Field.Store.YES));
        doc.add(new TextField("name", location.getContactPhone(), Field.Store.YES));

        // I don't know what "contexts" are supposed to be, they are also not gettable.
        return doc;
    }

    private void indexDocument(Document doc) throws Exception{
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, config);
        writer.addDocument(doc);
        writer.close();
    }
}
