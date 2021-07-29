package iris.backend_service.locations.search.db;

import iris.backend_service.locations.dto.LocationInformation;
import iris.backend_service.locations.search.SearchIndex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DBSearchIndex implements SearchIndex {

  @Getter
  private final LocationDAO locationDao;

  private final ModelMapper mapper;

  @Override
  public Page<LocationInformation> search(String keyword, Pageable pageable) {
	return locationDao.searchLocations(keyword, pageable).map(res -> {
	  var location = mapper.map(res, LocationInformation.class);
	  location.setId(res.getId().getLocationId());
	  location.setProviderId(res.getId().getProviderId());
	  return location;
	});
  }
}
