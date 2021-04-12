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
package de.healthIMIS.iris.public_server.data_submission.web;

import de.healthIMIS.iris.public_server.core.Feature;
import de.healthIMIS.iris.public_server.data_request.DataRequest;
import de.healthIMIS.iris.public_server.data_request.DataRequest.DataRequestIdentifier;
import de.healthIMIS.iris.public_server.data_request.DataRequestRepository;
import de.healthIMIS.iris.public_server.data_request.web.DataRequestRepresentations;
import de.healthIMIS.iris.public_server.data_submission.DataSubmission;
import de.healthIMIS.iris.public_server.data_submission.DataSubmissionRepository;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.vavr.control.Option;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller of the public end-points for apps to exchange data submissions.
 * 
 * @author Jens Kutzsche
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
		date = "2021-02-18T08:11:24.698Z[GMT]")
@RestController
@Slf4j
@RequiredArgsConstructor
public class DataSubmissionApiController implements DataSubmissionApi {

	private final @NonNull DataRequestRepository requests;
	private final @NonNull DataSubmissionRepository submissions;
	private final @NonNull DataRequestRepresentations representation;

	@Override
	public ResponseEntity<?> postContactsEventsSubmission(
			@Parameter(in = ParameterIn.PATH, description = "The code of a data request sent by the health department.",
					required = true, schema = @Schema()) @PathVariable("code") DataRequestIdentifier code,
			@Parameter(in = ParameterIn.DEFAULT, description = "", required = true,
					schema = @Schema()) @Valid @RequestBody ContactsEventsSubmissionDto body) {

		return handleRequest(code, body, body.getEncryptedData(), Feature.Contacts_Events);
	}

	@Override
	public ResponseEntity<?> postGuestsSubmission(
			@Parameter(in = ParameterIn.PATH, description = "The code of a data request sent by the health department.",
					required = true, schema = @Schema()) @PathVariable("code") DataRequestIdentifier code,
			@Parameter(in = ParameterIn.DEFAULT, description = "", required = true,
					schema = @Schema()) @Valid @RequestBody GuestsSubmissionDto body) {

		return handleRequest(code, body, body.getEncryptedData(), Feature.Guests);
	}

	private ResponseEntity<?> handleRequest(DataRequestIdentifier code, DataSubmissionDto dto, String encryptedData,
			Feature feature) {

		return createAndSaveDataSubmission(code, dto, encryptedData, feature)
				.map(it -> ResponseEntity.accepted().build())
				.getOrElse(ResponseEntity.notFound()::build);
	}

	private Option<DataSubmission> createAndSaveDataSubmission(DataRequestIdentifier code, @NotNull DataSubmissionDto dto,
			String encryptedData, Feature feature) {

		return Option.ofOptional(requests.findById(code))
				.filter(it -> requestMatchesFeature(it, feature))
				.onEmpty(() -> logMissingRequest(code, feature))
				.map(it -> createSubmission(it, dto, encryptedData, feature))
				.map(submissions::save)
				.map(this::log);
	}

	private boolean requestMatchesFeature(DataRequest dataRequest, Feature feature) {
		return dataRequest.getFeatures().contains(feature);
	}

	private DataSubmission createSubmission(DataRequest request, DataSubmissionDto dto, String encryptedData,
			Feature feature) {

		return new DataSubmission(request.getId(), request.getDepartmentId(), dto.getSecret(),
				dto.getKeyReference(), encryptedData, feature);
	}

	private DataSubmission log(DataSubmission submission) {

		log.debug("Submission - POST from public + saved: {} (Type: {}; Department: {})",
				submission.getRequestId().toString(), submission.getFeature().name(), submission.getDepartmentId());

		return submission;
	}

	private void logMissingRequest(DataRequestIdentifier code, Feature feature) {
		log.warn("Submission - POST from public not valid: {} (Type: {}", code, feature.name());
	}
}
