package iris.backend_service.locations.search;

import iris.backend_service.locations.dto.LocationInformation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchIndex {
	Page<LocationInformation> search(String keyword, Pageable pageable);
}
