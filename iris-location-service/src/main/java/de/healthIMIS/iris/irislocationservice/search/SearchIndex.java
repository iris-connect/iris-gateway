package de.healthIMIS.iris.irislocationservice.search;

import de.healthIMIS.iris.irislocationservice.dto.LocationInformation;

import java.util.List;

public interface SearchIndex {
    List<LocationInformation> search(String keyword);
}