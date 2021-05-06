/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.24).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package iris.public_server.data_submission.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iris.public_server.data_request.DataRequest.DataRequestIdentifier;
import iris.public_server.data_submission.web.dto.ContactsEventsSubmissionDto;
import iris.public_server.data_submission.web.dto.GuestsSubmissionDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
		date = "2021-02-18T08:11:24.698Z[GMT]")
public interface DataSubmissionApi {

	@Operation(
			summary = "Receives a data submission of contacts and events for the health department and transmits them in the further process.",
			description = "", tags = {})
	@ApiResponses(value = { @ApiResponse(responseCode = "202",
			description = "Submission was accepted and saved for later processing by the client in the health department."),

			@ApiResponse(responseCode = "401", description = "The client is unauthorized to access this API."),

			@ApiResponse(responseCode = "404", description = "The specified resource was not found."),

			@ApiResponse(responseCode = "422",
					description = "The transferred entity is not expected for the data request.") })
	@RequestMapping(value = "/data-submissions/{code}/contacts_events", consumes = { "application/json" },
			method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.ACCEPTED)
	ResponseEntity<?> postContactsEventsSubmission(
			@Parameter(in = ParameterIn.PATH, description = "The code of a data request sent by the health department.",
					required = true, schema = @Schema()) @PathVariable("code") DataRequestIdentifier code,
			@Parameter(in = ParameterIn.DEFAULT, description = "", required = true,
					schema = @Schema()) @Valid @RequestBody ContactsEventsSubmissionDto body);

	@Operation(
			summary = "Receives a data submission of guests for the health department and transmits them in the further process.",
			description = "", tags = {})
	@ApiResponses(value = { @ApiResponse(responseCode = "202",
			description = "Submission was accepted and saved for later processing by the client in the health department."),

			@ApiResponse(responseCode = "401", description = "The client is unauthorized to access this API."),

			@ApiResponse(responseCode = "404", description = "The specified resource was not found."),

			@ApiResponse(responseCode = "422",
					description = "The transferred entity is not expected for the data request.") })
	@RequestMapping(value = "/data-submissions/{code}/guests", consumes = { "application/json" },
			method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.ACCEPTED)
	ResponseEntity<?> postGuestsSubmission(
			@Parameter(in = ParameterIn.PATH, description = "The code of a data request sent by the health department.",
					required = true, schema = @Schema()) @PathVariable("code") DataRequestIdentifier code,
			@Parameter(in = ParameterIn.DEFAULT, description = "", required = true,
					schema = @Schema()) @Valid @RequestBody GuestsSubmissionDto body);

}