package de.healthIMIS.iris.irislocationservice;

interface FullTextSearch {
    LocationList search(String keyword);
}