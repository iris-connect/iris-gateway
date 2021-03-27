package de.healthIMIS.iris.irislocationservice;

import de.healthIMIS.iris.irislocationservice.dto.LocationList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;

@Validated
public interface LocationIndexApi {
    @Operation(summary = "Deletes contact information about a specific location identified by the provider owned ID.", description = "", security = {
            @SecurityRequirement(name = "ApiKeyAuth")    }, tags={ "Location Search Index" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location deleted"),

            @ApiResponse(responseCode = "401", description = "The client is unauthorized to access this API."),

            @ApiResponse(responseCode = "404", description = "The specified resource was not found.") })

    @RequestMapping(value = "/search-index/locations/{id}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLocationFromSearchIndex(@Parameter(in = ParameterIn.PATH, description = "The unique ID of a location in the context of the provider.", required=true, schema=@Schema()) @PathVariable("id") String id);

    @Operation(summary = "Recives contact information about locations that can be used to search for a specific location that was visited by a potentially infected person.", description = "Locations are identifed by an ID that is owned by the provider. If an ID already exists, the information is overidden. The IDs are handled separately for each provider based on their authentication, so there is no overlap of ID between providers.", security = {
            @SecurityRequirement(name = "ApiKeyAuth")    }, tags={ "Location Search Index" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Locations added to search index"),

            @ApiResponse(responseCode = "401", description = "The client is unauthorized to access this API."),

            @ApiResponse(responseCode = "422", description = "The transferred entity is not expected for the data request.") })
    @RequestMapping(value = "/search-index/locations",
            consumes = { "application/json; charset=UTF-8" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> postLocationsToSearchIndex(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody LocationList body);

    @Operation(summary = "", description = "", tags={  })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The response for the location search", content = @Content(schema = @Schema(implementation = LocationList.class))) })
    @RequestMapping(value = "/search/{search_keyword}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<LocationList> searchSearchKeywordGet(@DecimalMin("4")@Parameter(in = ParameterIn.PATH, description = "The search keyword", required=true, schema=@Schema()) @PathVariable("search_keyword") String searchKeyword);
}
