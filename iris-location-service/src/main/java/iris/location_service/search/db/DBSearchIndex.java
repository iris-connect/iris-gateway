package iris.location_service.search.db;

import iris.location_service.dto.LocationInformation;
import iris.location_service.search.SearchIndex;
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
