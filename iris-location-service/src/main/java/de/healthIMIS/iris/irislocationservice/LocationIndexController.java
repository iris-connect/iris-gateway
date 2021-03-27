package de.healthIMIS.iris.irislocationservice;
/*
 * Controller of the location index. Handles adding/removing entries from the index as well as search
 *
 * @author Kai Greshake
 */


@RestController
public class LocationIndexController implements SearchApi {

    private static final Logger log = LoggerFactory.getLogger(SearchApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public SearchApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }
    
    public ResponseEntity<Void> deleteLocationFromSearchIndex(@Parameter(in = ParameterIn.PATH, description = "The unique ID of a location in the context of the provider.", required=true, schema=@Schema()) @PathVariable("id") String id) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> postLocationsToSearchIndex(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody LocationList body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<LocationList> searchSearchKeywordGet(@DecimalMin("4")@Parameter(in = ParameterIn.PATH, description = "The search keyword", required=true, schema=@Schema()) @PathVariable("search_keyword") String searchKeyword) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<LocationList>(objectMapper.readValue("{\n  \"locations\" : [ {\n    \"contact\" : {\n      \"address\" : {\n        \"zip\" : \"80333\",\n        \"city\" : \"München\",\n        \"street\" : \"Türkenstr. 7\"\n      },\n      \"phone\" : \"die bleibt privat :-)\",\n      \"officialName\" : \"Darfichrein GmbH\",\n      \"representative\" : \"Silke \",\n      \"email\" : \"covid2@restaurant.de\",\n      \"ownerEmail\" : \"covid@restaurant.de\"\n    },\n    \"name\" : \"Restaurant Alberts\",\n    \"id\" : \"5eddd61036d39a0ff8b11fdb\",\n    \"publicKey\" : \"publicKey\",\n    \"contexts\" : [ {\n      \"name\" : \"Raum 0815\",\n      \"id\" : \"5f4bfff742c1bf5f72918851\"\n    }, {\n      \"name\" : \"Raum 0815\",\n      \"id\" : \"5f4bfff742c1bf5f72918851\"\n    } ]\n  }, {\n    \"contact\" : {\n      \"address\" : {\n        \"zip\" : \"80333\",\n        \"city\" : \"München\",\n        \"street\" : \"Türkenstr. 7\"\n      },\n      \"phone\" : \"die bleibt privat :-)\",\n      \"officialName\" : \"Darfichrein GmbH\",\n      \"representative\" : \"Silke \",\n      \"email\" : \"covid2@restaurant.de\",\n      \"ownerEmail\" : \"covid@restaurant.de\"\n    },\n    \"name\" : \"Restaurant Alberts\",\n    \"id\" : \"5eddd61036d39a0ff8b11fdb\",\n    \"publicKey\" : \"publicKey\",\n    \"contexts\" : [ {\n      \"name\" : \"Raum 0815\",\n      \"id\" : \"5f4bfff742c1bf5f72918851\"\n    }, {\n      \"name\" : \"Raum 0815\",\n      \"id\" : \"5f4bfff742c1bf5f72918851\"\n    } ]\n  } ]\n}", LocationList.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<LocationList>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<LocationList>(HttpStatus.NOT_IMPLEMENTED);
    }

}