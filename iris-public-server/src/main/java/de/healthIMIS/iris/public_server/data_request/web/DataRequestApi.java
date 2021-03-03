/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.24).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package de.healthIMIS.iris.public_server.data_request.web;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.healthIMIS.iris.public_server.data_request.DataRequest.DataRequestIdentifier;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-02-18T08:11:24.698Z[GMT]")
public interface DataRequestApi {

	@Operation(summary = "Returns the data request for a code.", description = "", tags = {})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200",
			description = "A data request with all parameters relevant for the data submission and the links to the next possible steps for data submission.",
			content = @Content(schema = @Schema(implementation = DataRequestDto.class))),

		@ApiResponse(responseCode = "401", description = "The client is unauthorized to access this API."),

		@ApiResponse(responseCode = "404", description = "The specified resource was not found.") })
	@RequestMapping(value = "/data-requests/{code}",
		produces = {
			"application/hal+json" },
		method = RequestMethod.GET)
	ResponseEntity<?> getDataRequestByCode(
		@Parameter(in = ParameterIn.PATH,
			description = "The code of a data request sent by the health department.",
			required = true,
			schema = @Schema()) @PathVariable("code") DataRequestIdentifier code);

	@Operation(summary = "Returns the data request for a teleCode.", description = "", tags = {})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200",
			description = "A data request with all parameters relevant for the data submission and the links to the next possible steps for data submission.",
			content = @Content(schema = @Schema(implementation = DataRequestDto.class))),

		@ApiResponse(responseCode = "401", description = "The client is unauthorized to access this API."),

		@ApiResponse(responseCode = "404", description = "The specified resource was not found.") })
	@RequestMapping(value = "/data-requests",
		produces = {
			"application/hal+json" },
		method = RequestMethod.GET)
	ResponseEntity<?> getDataRequestByTeleCode(
		@NotNull @Parameter(in = ParameterIn.QUERY,
			description = "The teleCode of a data request given by the health department.",
			required = true,
			schema = @Schema()) @Valid @RequestParam(value = "teleCode", required = true) String teleCode,
		@NotNull @Parameter(in = ParameterIn.QUERY,
			description = "The zip-code from the address of the person whose data is to be requested.",
			required = true,
			schema = @Schema()) @Valid @RequestParam(value = "zip", required = true) String zip);

}
