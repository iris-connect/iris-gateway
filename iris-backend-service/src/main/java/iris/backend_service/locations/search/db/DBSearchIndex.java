package iris.backend_service.locations.search.db;

import iris.backend_service.locations.dto.LocationInformation;
import iris.backend_service.locations.search.SearchIndex;
import iris.backend_service.locations.search.db.model.Location;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

import org.hibernate.search.engine.search.predicate.dsl.PredicateFinalStep;
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
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

	private static final String[] FIELDS = { "officialName_search", "representative_search",
			"street_search", "contactAddressZip", "contactEmail", "contactPhone" };

	private final SearchSession searchSession;
	private final ModelMapper mapper;

	@Override
	public Page<LocationInformation> search(String keyword, Pageable pageable) {

		var result = searchSession.search(Location.class)
				.where(f -> createQuery(keyword, f))
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

	private PredicateFinalStep createQuery(String keyword, SearchPredicateFactory f) {

		if (keyword.startsWith("query:")) {
			return f.simpleQueryString().fields(FIELDS).field("name_search").field("contactAddressCity")
					.matching(keyword.substring(6));
		}

		return f.bool()
				.should(f2 -> f2.match()
						.field("name_search").boost(2f)
						.field("contactAddressCity").boost(2.5f)
						.fields(FIELDS)
						.matching(keyword)
						.fuzzy())
				.should(f2 -> f2.wildcard()
						.field("name_search").boost(2f)
						.field("contactAddressCity").boost(1.5f)
						.fields(FIELDS)
						.matching(String.format("*%s*", keyword)));
	}

	private LocationInformation toLocationInformation(Location res) {

		var location = mapper.map(res, LocationInformation.class);
		location.setId(res.getId().getLocationId());
		location.setProviderId(res.getId().getProviderId());

		return location;
	}
}
