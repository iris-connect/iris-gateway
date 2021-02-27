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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.healthIMIS.iris.public_server.data_submission.DataSubmission;
import de.healthIMIS.iris.public_server.data_submission.DataSubmissionRepository;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller of the internal end-points for health department site to exchange data submissions.
 * 
 * @author Jens Kutzsche
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class DataSubmissionHdController {

	private final @NonNull DataSubmissionRepository submissions;

	@GetMapping("/hd/data-submissions")
	List<DataSubmissionInternalOutputDto> getDataSubmissions() {

		var dataSubmissions = submissions.findAll();

		submissions.deleteAll(dataSubmissions);

		var dtos =
			StreamSupport.stream(dataSubmissions.spliterator(), false).map(it -> DataSubmissionInternalOutputDto.of(it)).collect(Collectors.toList());

		log.debug(
			"Submission - GET hd server: {}",
			dtos.stream().map(DataSubmissionInternalOutputDto::getRequestId).collect(Collectors.joining(", ")));

		return dtos;
	}

	@Data
	static class DataSubmissionInternalOutputDto {

		static DataSubmissionInternalOutputDto of(DataSubmission submission) {
			return new DataSubmissionInternalOutputDto(
				submission.getId().toString(),
				submission.getRequestId().toString(),
				submission.getDepartmentId().toString(),
				submission.getSalt(),
				submission.getKeyReferenz(),
				submission.getEncryptedData());
		}

		private final String id;

		private final String requestId;

		private final String departmentId;

		private final String salt;

		private final String keyReferenz;

		private final String encryptedData;
	}
}
