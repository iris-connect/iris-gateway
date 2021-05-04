package iris.location_service.search;

import iris.location_service.dto.LocationInformation;

import java.util.List;

public interface SearchIndex {
    List<LocationInformation> search(String keyword);
}