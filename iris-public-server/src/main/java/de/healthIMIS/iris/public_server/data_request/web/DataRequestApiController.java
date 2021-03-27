/*******************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package de.healthIMIS.iris.public_server.data_request.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import de.healthIMIS.iris.public_server.data_request.DataRequest;
import de.healthIMIS.iris.public_server.data_request.DataRequest.DataRequestIdentifier;
import de.healthIMIS.iris.public_server.data_request.DataRequestRepository;
import de.healthIMIS.iris.public_server.rki.HealthDepartments;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller of the public end-points for apps to exchange data requests.
 * 
 * @author Jens Kutzsche
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-02-18T08:11:24.698Z[GMT]")
@RestController
@RequiredArgsConstructor
@Slf4j
public class DataRequestApiController implements DataRequestApi {

	private final @NonNull DataRequestRepository requests;
	private final @NonNull DataRequestRepresentations representation;
	private final @NonNull HealthDepartments rkiDepartments;

	@Override
	public ResponseEntity<?> getDataRequestByCode(
		@Parameter(in = ParameterIn.PATH,
			description = "The code of a data request sent by the health department.",
			required = true,
			schema = @Schema()) @PathVariable("code") DataRequestIdentifier code) {

		return requests.findById(code)
			.map(this::log)
			.map(representation::toRepresentation)
			.map(ResponseEntity::ok)
			.orElseGet(() -> ResponseEntity.notFound().build());
	}

	private DataRequest log(DataRequest request) {

		log.debug("Request - GET from public: {}", request.getId().toString());

		return request;
	}
}
