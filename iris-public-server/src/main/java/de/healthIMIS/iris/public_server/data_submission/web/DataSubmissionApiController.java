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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

	private ResponseEntity<?> handleRequest(DataRequestIdentifier code, DataSubmissionDto body, String encryptedData,
			Feature feature) {

		return createAndSaveDataSubmission(code, body, encryptedData, feature).map(representation::toRepresentation)
				.map(it -> ResponseEntity.accepted().build()).orElseGet(ResponseEntity.notFound()::build);
	}

	private Optional<DataRequest> createAndSaveDataSubmission(DataRequestIdentifier code, @NotNull DataSubmissionDto body,
			String encryptedData, Feature feature) {

		return requests.findById(code).filter(it -> matchesOnCheckCode(body.getCheckCode(), it))
				.filter(it -> matchesFeature(feature, it)).map(it -> {

					var submission = new DataSubmission(it.getId(), it.getDepartmentId(), body.getSecret(), body.getKeyReferenz(),
							encryptedData, feature);
					submission = submissions.save(submission);

					log.debug("Submission - POST from public + saved: {} (Type: {}; Department: {})",
							submission.getRequestId().toString(), submission.getClass().getSimpleName(),
							submission.getDepartmentId());

					return it;
				});
	}

	private boolean matchesOnCheckCode(@NotNull List<String> checkCodes, DataRequest dataRequest) {

		var requestCodes = new HashSet<String>(3);
		if (dataRequest.getCheckCodeName() != null) {
			requestCodes.add(dataRequest.getCheckCodeName());
		}
		if (dataRequest.getCheckCodeDayOfBirth() != null) {
			requestCodes.add(dataRequest.getCheckCodeDayOfBirth());
		}
		if (dataRequest.getCheckCodeRandom() != null) {
			requestCodes.add(dataRequest.getCheckCodeRandom());
		}

		return checkCodes.stream().anyMatch(requestCodes::contains);
	}

	private boolean matchesFeature(Feature feature, DataRequest dataRequest) {
		return dataRequest.getFeatures().contains(feature);
	}
}
