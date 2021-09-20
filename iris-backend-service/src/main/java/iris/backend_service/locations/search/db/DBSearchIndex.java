package iris.backend_service.locations.search.db;

import iris.backend_service.locations.dto.LocationInformation;
import iris.backend_service.locations.search.SearchIndex;
import iris.backend_service.locations.search.db.model.Location;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

import org.hibernate.search.engine.search.sort.dsl.SortOrder;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DBSearchIndex implements SearchIndex {

	private static final String[] FIELDS = { "name_search", "officialName_search", "representative_search",
			"street_search", "contactAddressCity", "contactAddressZip", "contactEmail", "contactPhone" };

	private final SearchSession searchSession;
	private final ModelMapper mapper;

	@Override
	public Page<LocationInformation> search(String keyword, Pageable pageable) {

		var result = searchSession.search(Location.class)
				.where(f -> f.bool()
						.should(f2 -> f2.match()
								.fields(FIELDS)
								.matching(keyword)
								.fuzzy())
						.should(f2 -> f2.wildcard()
								.fields(FIELDS)
								.matching(String.format("*%s*", keyword))))
				.sort(f -> f.composite(b -> {
					if (pageable != null) {
						pageable.getSort().forEach(s -> {
							b.add(f.field(s.getProperty()).order(s.isAscending() ? SortOrder.ASC : SortOrder.DESC));
						});
					}
				}))
				.fetch((int) pageable.getOffset(),
						pageable.getPageSize());

		var locations = result.hits().stream()
				.map(this::toLocationInformation)
				.collect(Collectors.toList());

		return new PageImpl<>(locations, pageable, result.total().hitCount());
	}

	private LocationInformation toLocationInformation(Location res) {

		var location = mapper.map(res, LocationInformation.class);
		location.setId(res.getId().getLocationId());
		location.setProviderId(res.getId().getProviderId());

		return location;
	}
}
