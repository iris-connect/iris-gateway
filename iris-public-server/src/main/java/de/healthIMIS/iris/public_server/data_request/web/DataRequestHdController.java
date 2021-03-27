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

import de.healthIMIS.iris.public_server.core.Feature;
import de.healthIMIS.iris.public_server.data_request.DataRequest;
import de.healthIMIS.iris.public_server.data_request.DataRequest.DataRequestIdentifier;
import de.healthIMIS.iris.public_server.data_request.DataRequest.Status;
import de.healthIMIS.iris.public_server.data_request.DataRequestRepository;
import de.healthIMIS.iris.public_server.department.Department.DepartmentIdentifier;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller of the internal end-points for health department site to exchange data requests.
 * 
 * @author Jens Kutzsche
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class DataRequestHdController {

	private final @NonNull DataRequestRepository requests;

	@PutMapping("/hd/data-requests/{id}")
	@ResponseStatus(HttpStatus.OK)
	DataRequestInternalInputDto putDataRequest(@PathVariable("id") DataRequestIdentifier id,
			@Valid @RequestBody DataRequestInternalInputDto payload, Errors errors) {

		var dataRequest = new DataRequest(id, DepartmentIdentifier.of(payload.departmentId), payload.getRkiCode(),
				payload.getTeleCode(), payload.checkCodeName, payload.checkCodeDayOfBirth, payload.checkCodeRandom,
				payload.requestStart, payload.requestEnd, payload.getRequestDetails(), payload.features, payload.status);

		try {
			requests.deleteById(dataRequest.getId());
		} catch (EmptyResultDataAccessException e) {}
		requests.save(dataRequest);

		log.debug("Request - PUT from hd server + saved: {} (Checkcodes: {}, {} and {})", dataRequest.getId().toString(),
				dataRequest.getCheckCodeName(), dataRequest.getCheckCodeDayOfBirth(), dataRequest.getCheckCodeRandom());

		return payload;
	}

	@Data
	static class DataRequestInternalInputDto {

		private UUID departmentId;
		private String rkiCode;

		private String teleCode;

		private String checkCodeName;
		private String checkCodeDayOfBirth;
		private String checkCodeRandom;

		private Instant requestStart;
		private Instant requestEnd;

		private String requestDetails;

		private Set<Feature> features;
		private Status status;
	}
}
