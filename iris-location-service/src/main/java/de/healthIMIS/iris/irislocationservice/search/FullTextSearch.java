package de.healthIMIS.iris.irislocationservice.search;

import de.healthIMIS.iris.irislocationservice.dto.LocationList;

interface FullTextSearch {
    LocationList search(String keyword);
}