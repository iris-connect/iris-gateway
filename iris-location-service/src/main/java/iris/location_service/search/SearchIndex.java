package iris.location_service.search;

import iris.location_service.dto.LocationInformation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchIndex {
	Page<LocationInformation> search(String keyword, Pageable pageable);
}
