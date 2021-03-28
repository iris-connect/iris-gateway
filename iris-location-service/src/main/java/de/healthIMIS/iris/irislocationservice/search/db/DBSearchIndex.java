package de.healthIMIS.iris.irislocationservice.search.db;

import de.healthIMIS.iris.irislocationservice.dto.LocationInformation;
import de.healthIMIS.iris.irislocationservice.search.LocationSearchEntry;
import de.healthIMIS.iris.irislocationservice.search.SearchIndex;
import de.healthIMIS.iris.irislocationservice.search.db.model.Location;
import de.healthIMIS.iris.irislocationservice.search.db.model.LocationIdentifier;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DBSearchIndex implements SearchIndex {

    private final LocationRepository repo;

    private final ModelMapper mapper;

    @Override
    public List<LocationSearchEntry> search(String keyword) {

        return repo.findByNameContaining(keyword).stream().map(res -> {

            var location = mapper.map(res, LocationInformation.class);
            location.setId(res.getId().getLocationId());

            var entry = new LocationSearchEntry();
            entry.setProviderId(res.getId().getProviderId());
            entry.setLocationInformation(location);

            return entry;
        }).collect(Collectors.toList());

    }

    @Override
    public void put(List<LocationSearchEntry> locations) {

        var data = locations.stream().map(entry -> {
            var location = mapper.map(entry.getLocationInformation(), Location.class);
            location.setId(new LocationIdentifier(entry.getProviderId(), entry.getLocationInformation().getId()));
            return location;
        }).collect(Collectors.toList());

        repo.saveAll(data);
    }

    @Override
    public void delete(String providerId, String id) {
        repo.deleteById(new LocationIdentifier(providerId, id));
    }


}
