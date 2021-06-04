package iris.location_service.search.lucene;

import iris.location_service.dto.LocationAddress;
import iris.location_service.dto.LocationContact;
import iris.location_service.dto.LocationInformation;
import iris.location_service.search.SearchIndex;
import iris.location_service.search.db.model.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LuceneIndexService implements SearchIndex {

    @Setter
    private Analyzer analyzer;

    @Getter
    private Directory dir;

    private LuceneSearcher luceneSearcher;

    private IndexWriter writer;

    //private ModelMapper mapper;

    @Setter
    @Autowired
    private LuceneIndexServiceProperties luceneIndexServiceProperties;

    @PreDestroy
    public void preDestroy() throws IOException {
        writer.close();
    }

    @PostConstruct
    public void postConstruct() {
        try {
        analyzer = new StandardAnalyzer();

        //mapper = new ModelMapper();
        //mapper.getConfiguration().setAmbiguityIgnored(true);

        var config = new IndexWriterConfig(analyzer);

        dir = FSDirectory.open(Paths.get(luceneIndexServiceProperties.getIndexDirectory()));

        writer = new IndexWriter(dir, config);

        luceneSearcher = new LuceneSearcher(dir, analyzer);
        }catch (IOException e){
            log.error("Error can´t init LuceneIndexService: " + e);
            System.out.println("Error in post construct: " + e);
        }
    }

    @Override
    public List<LocationInformation> search(String keyword){
        try{

            // search
            List<Document> results = luceneSearcher.search(keyword);

            // parse to location list
            List<LocationInformation> locationList = new ArrayList<>();
            for(Document entry:results){
                locationList.add(createLocationInformation(entry));
            }
            return locationList;
        } catch (IOException | ParseException e) {
            log.error("Error while searching for [{}]: ", keyword, e);
            return new ArrayList<>();
        }
    }

    public void indexLocations(List<Location> locations){
        try {
            for(Location location:locations){
                // gibt es die Location bereits?
                // Zwei Anbieter benutzen die selbe id.
                //Document name -> documentxyz+"-"+providerid+"-"+id
                // Provider A und B
                // Location: A -> ID: X
                // Location: B -> ID: X

                //TODO was ist wenn der User eine andere App nutzt zum updaten?

                // See LocationIdentifier
                // if location does not exist
                // if(search(location.getName()).isEmpty()){
                    indexNewDocument(createDocument(location));
                //}else{
                //    indexExistingDocument(createDocument(location));
                //}

                // if location exists updateDocument(createDocument(location));
            }
        }catch (Exception e){
            log.error("Error while indexLocations: ", e);
        }
    }

    public void deleteLocation(Location location){
        try {
            deleteDocumentById(String.valueOf(location.getId()));
        }catch (Exception e){
            log.error("Error while deleting Location: ", e);
        }
    }

    public void deleteLocations(List<Location> locations){
        for(Location location: locations){
            deleteLocation(location);
        }
    }

    private Document createDocument(Location location){
        Document doc = new Document();
        // Is the ID relevant for us?
        // doc.add(new TextField("name", location.getId().toString(), Field.Store.YES));
        // "publicKey" is not gettable
        // recover: 1, Location: 1 (1, 1)
        // e-Guest: 2, Location: 1 (2, 1)
        doc.add(new TextField("Id", location.getId().getLocationId(), Field.Store.YES));
        doc.add(new TextField("ProviderId", location.getId().getProviderId(), Field.Store.YES));
        doc.add(new TextField("Name", location.getName(), Field.Store.YES));
        doc.add(new TextField("ContactOfficialName", location.getContactOfficialName(), Field.Store.YES));
        doc.add(new TextField("ContactRepresentative", location.getContactRepresentative(), Field.Store.YES));
        doc.add(new TextField("ContactAddressStreet", location.getContactAddressStreet(), Field.Store.YES));
        doc.add(new TextField("ContactAddressCity", location.getContactAddressCity(), Field.Store.YES));
        doc.add(new TextField("ContactAddressZip", location.getContactAddressZip(), Field.Store.YES));
        doc.add(new TextField("ContactOwnerEmail", location.getContactOwnerEmail(), Field.Store.YES));
        doc.add(new TextField("ContactEmail", location.getContactEmail(), Field.Store.YES));
        doc.add(new TextField("ContactPhone", location.getContactPhone(), Field.Store.YES));
        // I don't know what "contexts" are supposed to be, they are also not gettable.
        return doc;
    }

    private void indexNewDocument(Document doc) throws Exception {
        writer.addDocument(doc);
        writer.commit();
    }

    private void indexExistingDocument(Document doc) throws Exception {
        // ToDo: Implement based on existing ID
    }

    private void deleteDocumentById(String id) throws Exception {
        // ToDo: Implement based on existing ID
        QueryParser parser = new QueryParser("Id", analyzer);
        Query query = parser.parse(id);
        writer.deleteDocuments(query);
        writer.flush();
    }

    public LocationInformation createLocationInformation(Document document) {
        // Missing: Mapping Document -> Location (verbinden mit Location -> Document)
        //var locationInformation = mapper.map(document , LocationInformation.class);
        var locationInformation = new LocationInformation();

        var locationContact = new LocationContact();
        var locationAddress = new LocationAddress();
        locationAddress.setStreet(document.get("ContactAddressStreet"));
        locationAddress.setCity(document.get("ContactAddressCity"));
        locationAddress.setZip(document.get("ContactAddressZip"));

        locationContact.setAddress(locationAddress);
        locationContact.setEmail(document.get("ContactEmail"));
        locationContact.setOfficialName(document.get("ContactOfficialName"));
        locationContact.setOwnerEmail(document.get("ContactOwnerEmail"));
        locationContact.setPhone(document.get("ContactPhone"));
        locationContact.setRepresentative(document.get("ContactRepresentative"));

        locationInformation.setId(document.get("Id"));
        locationInformation.setProviderId( document.get("ProviderId"));
        locationInformation.setName(document.get("Name"));
        locationInformation.setContact(locationContact);
        return locationInformation;
        // TODO: ADD all other information like Context and PublicKey maybe with ModelMapper?
        // Context and PublicKey not in document ??
    }

    public void setDir(String path) throws IOException{
        this.dir = FSDirectory.open(Paths.get(path));
    }
}
