package de.healthIMIS.iris.irislocationservice.search;

import java.util.List;

public interface SearchIndex {

    List<LocationSearchEntry> search(String keyword);

    void put(List<LocationSearchEntry> locations);

    void delete(String providerId, String locationId);

}