/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.24).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package iris.public_server.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
		date = "2021-02-18T08:11:24.698Z[GMT]")
public interface DefaultApi {

	@Operation(summary = "Entry point returns next steps by links.",
			description = "Entry point for the clients of the API. From here, they are guided through the API by means of links.",
			tags = {})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Links for the next posible steps."),

			@ApiResponse(responseCode = "401", description = "The client is unauthorized to access this API.") })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	RepresentationModel<?> rootGet();

}