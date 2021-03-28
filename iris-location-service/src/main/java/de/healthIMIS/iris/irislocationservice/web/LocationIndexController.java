package de.healthIMIS.iris.irislocationservice.web;
/*
 * Controller of the location index. Handles adding/removing entries from the index as well as search
 *
 * @author Kai Greshake
 */


import com.fasterxml.jackson.databind.ObjectMapper;
import de.healthIMIS.iris.irislocationservice.search.db.DBSearchIndex;
import de.healthIMIS.iris.irislocationservice.search.db.LocationRepository;
import de.healthIMIS.iris.irislocationservice.dto.LocationInformation;
import de.healthIMIS.iris.irislocationservice.dto.LocationList;
import de.healthIMIS.iris.irislocationservice.search.db.model.Location;
import de.healthIMIS.iris.irislocationservice.search.db.model.LocationIdentifier;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class LocationIndexController implements LocationIndexApi {

    private static final Logger log = LoggerFactory.getLogger(LocationIndexApi.class);

    private final ObjectMapper objectMapper;

    /*
    TODO: This placeholder is for information we will get from authenticating the API requests-
    i.e. which provider sent the request. Mocked to be constant 0 for now.
     */
    private final String temporary_provider_id = "00000-00000-00000-00000";

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private DBSearchIndex index;

    public LocationIndexController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<Void> deleteLocationFromSearchIndex(@Parameter(in = ParameterIn.PATH, description = "The unique ID of a location in the context of the provider.", required=true, schema=@Schema()) @PathVariable("id") String id) {
        // TODO: Authenticate API Access

        // Construct a new ID to match the (provider, id) pair key
        LocationIdentifier ident = new LocationIdentifier(temporary_provider_id, id);

        Optional<Location> match = locationRepository.findById(ident);
        if (match.isPresent()) {
            locationRepository.delete(match.get());
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Void> postLocationsToSearchIndex(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody LocationList body) {
        // TODO: Authenticate API Access

        // TODO: Define sensible limits for this API
        
        var locations = body.getLocations();
        if (locations != null) {
            var data = body.getLocations().stream().map(entry -> {
                var location = mapper.map(entry, Location.class);
                // For the search index, we are only interested in a subset of the data structure for location information
                // Can be replaced
                location.setId(new LocationIdentifier(temporary_provider_id, entry.getId()));
                return location;
            }).collect(Collectors.toList());

            locationRepository.saveAll(data);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public ResponseEntity<LocationList> searchSearchKeywordGet(@DecimalMin("4")@Parameter(in = ParameterIn.PATH, description = "The search keyword", required=true, schema=@Schema()) @PathVariable("search_keyword") String searchKeyword) {
        // TODO: Authenticate API Access

        return new ResponseEntity<LocationList>(new LocationList(index.search(searchKeyword)), HttpStatus.OK);
    }

}