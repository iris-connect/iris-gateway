package iris.location_service.search.lucene;

import iris.location_service.dto.LocationInformation;
import iris.location_service.search.SearchIndex;

import java.util.List;

public class LuceneSearchIndex implements SearchIndex {
    @Override
    public List<LocationInformation> search(String keyword) {
        return null;
    }
}
