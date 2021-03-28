package de.healthIMIS.iris.irislocationservice.search.db;

import de.healthIMIS.iris.irislocationservice.dto.LocationInformation;
import de.healthIMIS.iris.irislocationservice.search.SearchIndex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DBSearchIndex implements SearchIndex {

    @Getter
    private final LocationRepository repo;

    private final ModelMapper mapper;

    @Override
    public List<LocationInformation> search(String keyword) {

        return repo.findByNameContaining(keyword).stream()
                .limit(20)
                .map(res -> {
                    var location = mapper.map(res, LocationInformation.class);
                    location.setId(res.getId().getLocationId());
                    return location;
                }).collect(Collectors.toList());

    }
}
