package de.healthIMIS.iris.irislocationservice.web;
/*
 * Controller of the location index. Handles adding/removing entries from the index as well as search
 *
 * @author Kai Greshake
 */


import com.fasterxml.jackson.databind.ObjectMapper;
import de.healthIMIS.iris.irislocationservice.search.db.LocationRepository;
import de.healthIMIS.iris.irislocationservice.dto.LocationInformation;
import de.healthIMIS.iris.irislocationservice.dto.LocationList;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;

@RestController
public class LocationIndexController implements LocationIndexApi {

    private static final Logger log = LoggerFactory.getLogger(LocationIndexApi.class);

    private final ObjectMapper objectMapper;

    @Autowired
    private LocationRepository locationRepository;

    public LocationIndexController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }



    public ResponseEntity<Void> deleteLocationFromSearchIndex(@Parameter(in = ParameterIn.PATH, description = "The unique ID of a location in the context of the provider.", required=true, schema=@Schema()) @PathVariable("id") String id) {
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> postLocationsToSearchIndex(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody LocationList body) {
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<LocationList> searchSearchKeywordGet(@DecimalMin("4")@Parameter(in = ParameterIn.PATH, description = "The search keyword", required=true, schema=@Schema()) @PathVariable("search_keyword") String searchKeyword) {



        var location = new LocationInformation();
        location.setId("abc");
        location.setName("Pizzeria 1415");



        LocationList locationList = new LocationList();


        return new ResponseEntity<LocationList>(HttpStatus.NOT_IMPLEMENTED);
    }

}