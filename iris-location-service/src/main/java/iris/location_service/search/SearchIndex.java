package iris.location_service.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import iris.location_service.dto.LocationInformation;

public interface SearchIndex {
	Page<LocationInformation> search(String keyword, Pageable pageable);
}
