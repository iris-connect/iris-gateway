package de.healthIMIS.iris.irislocationservice;

import de.healthIMIS.iris.irislocationservice.dto.LocationList;

interface FullTextSearch {
    LocationList search(String keyword);
}