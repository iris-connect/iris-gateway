package iris.location_service.search.lucene;

import iris.location_service.dto.LocationAddress;
import iris.location_service.dto.LocationContact;
import iris.location_service.dto.LocationInformation;
import iris.location_service.search.SearchIndex;
import iris.location_service.search.db.model.Location;
import iris.location_service.search.db.model.LocationIdentifier;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 * Initializes the lucene Searcher
 * provides functions for creating/deleting/searching/indexing locations
 */
@Service
@Slf4j
public class LuceneIndexService implements SearchIndex {

    private Analyzer analyzer;

    @Getter
    private Directory dir;

    private LuceneSearcher luceneSearcher;

    private IndexWriter writer;

    private @NotNull ModelMapper mapper;

    @Setter
    @Autowired
    private LuceneIndexServiceProperties luceneIndexServiceProperties;

    /**
     * closes the indexwriter
     * @throws IOException when indexwriter fails
     */
    @PreDestroy
    public void preDestroy() throws IOException {
        writer.close();
    }

    /**
     * initializes analyzer and lucenesearcher
     */
    @PostConstruct
    public void postConstruct() {
        try {

            Map<String, Analyzer> map = new HashMap<>();
            map.put("Id",new IdAnalyzer());
            map.put("ProviderId",new IdAnalyzer());
            analyzer =  new PerFieldAnalyzerWrapper(new StandardAnalyzer(), map);

            var config = new IndexWriterConfig(analyzer);

            dir = FSDirectory.open(Paths.get(luceneIndexServiceProperties.getIndexDirectory()));

            writer = new IndexWriter(dir, config);
            writer.commit();

            luceneSearcher = new LuceneSearcher(dir, analyzer);
        }catch (IOException e){
            log.error("Error can´t init LuceneIndexService: " + e);
            System.out.println("Error in post construct: " + e);
        }

        mapper = new ModelMapper();
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

    /**
     * searches for a location by its location identifier (requires provider and location id)
     * @param providerId the provider ID
     * @param locationId the location ID
     * @return a location information object (or null)
     */
    public Optional<LocationInformation> searchByProviderIdAndLocationId(String providerId, String locationId){
        try {
            Document location = luceneSearcher.searchById(new LocationIdentifier(providerId, locationId));
            return Optional.of(createLocationInformation(location));
        } catch (IOException e) {
            log.error("Error while searching by LocationIdentifier for [{},{}]", providerId, locationId, e);
            return Optional.empty();
        }
    }

    /**
     * indexes a given list of locations
     * if a given location exists it will be replaced
     * @param providerId the provider id
     * @param locations the list of locations
     */
    public void indexLocations(UUID providerId, List<LocationInformation> locations){

        for(LocationInformation locationInfo :locations){
            // ToDo: Fix multiple documents with the same locationInfo and provider id exist -> TestCase.
            // ToDo: Fix inconsistencies during startup and shutdown.
            LocationIdentifier locIdent = new LocationIdentifier(providerId.toString(), locationInfo.getId());
            try {
                if(luceneSearcher.searchById(locIdent) != null){
                    deleteDocumentById(locIdent);
                }
                Location location = createLocation(locationInfo, locIdent);
                indexDocument(createDocument(location));
            }catch (Exception e){
                log.error("Error while index locationInfo: ", e);
            }
        }
    }

    /**
     * deletes a location of a given id
     *
     * @param providerID the provider id
     * @param locationId the location id
     * @return bool whether there was no error or not
     */
    public boolean deleteLocation(UUID providerID, String locationId){
        LocationIdentifier locIdent = new LocationIdentifier(providerID.toString(), locationId);
        try {
            deleteDocumentById(locIdent);
        }catch (Exception e){
            log.error("Error while deleting location: ", e);
            return false;
        }
        return true;
    }

    /**
     * creates a new document for a given location
     * @param location the location object
     * @return the document
     */
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

    /**
     * adds a given document to the index writer
     * @param doc the document
     * @throws Exception thrown when writer fails
     */
    private void indexDocument(Document doc) throws Exception {
        writer.addDocument(doc);
        writer.commit();
    }

    /**
     * deletes a document by its location identifier
     * @param locIndent the location identifier
     * @throws Exception thrown when the writer fails
     */
    private void deleteDocumentById(LocationIdentifier locIndent) throws Exception {
        String providerId = locIndent.getProviderId();
        String locationId = locIndent.getLocationId();
        // ToDo: Implement based on existing ID
        BooleanQuery.Builder finalQuery = new BooleanQuery.Builder();
        finalQuery.add(new QueryParser("ProviderId", analyzer).parse(providerId), BooleanClause.Occur.MUST);
        finalQuery.add(new QueryParser("Id", analyzer).parse(locationId), BooleanClause.Occur.MUST);
        writer.deleteDocuments(finalQuery.build());
        writer.commit();
    }

    /**
     * converts a given document into a location information object
     * @param document the document
     * @return the location information object
     */
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
    }

    /**
     * creates a location object from an identifier and a location information object
     * @param locationInfo the location information
     * @param locIdent the location identifier
     * @return the new location object
     */
    private Location createLocation(LocationInformation locationInfo, LocationIdentifier locIdent){
        // Reset possibly sent provider id. We need to ensure this comes from
        // the authentication system and isn't user-provided!
        var location = mapper.map(locationInfo, Location.class);

        // For the search index, we are only interested in a subset of the data structure for location information
        // Can be replaced
        location.setId(locIdent);

        return location;
    }

    /**
     * updates the lucene index directory
     * @param path the new directory path
     * @throws IOException thrown if path does not exist
     */
    public void setDir(String path) throws IOException{
        this.dir = FSDirectory.open(Paths.get(path));
    }
}
