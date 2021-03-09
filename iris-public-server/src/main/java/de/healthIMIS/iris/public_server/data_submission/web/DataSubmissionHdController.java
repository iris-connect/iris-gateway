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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.healthIMIS.iris.public_server.core.Feature;
import de.healthIMIS.iris.public_server.data_submission.DataSubmission;
import de.healthIMIS.iris.public_server.data_submission.DataSubmissionRepository;
import de.healthIMIS.iris.public_server.department.Department.DepartmentIdentifier;
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

//	@GetMapping("/hd/data-submissions")
//	HttpEntity<List<DataSubmissionInternalOutputDto>> getDataSubmissions(@RequestParam("from") String fromStr) {
//
//		var from = LocalDateTime.parse(fromStr);
//
//		// Shall prevent that within one millisecond (accuracy of the Last-Modified header) after
//		// loading additional records are added and thus records are transmitted twice or are forgotten.
//		var to = LocalDateTime.now().withNano(0);
//
//		var dataSubmissions = submissions.findAllByMetadataLastModifiedIsBetweenOrderByMetadataLastModified(from, to);
//
//		var dtos = dataSubmissions.map(DataSubmissionInternalOutputDto::of).toList();
//
//		log.debug(
//			"Submission - GET from hd server: {}",
//			dtos.stream().map(DataSubmissionInternalOutputDto::getRequestId).collect(Collectors.joining(", ")));
//
//		return ResponseEntity.ok().lastModified(to.atZone(ZoneId.systemDefault())).body(dtos);
//	}

	@GetMapping("/hd/data-submissions")
	HttpEntity<List<DataSubmissionInternalOutputDto>> getDataSubmissions(
		@RequestParam("departmentId") DepartmentIdentifier departmentId,
		@RequestParam("from") String fromStr) {

		var from = LocalDateTime.parse(fromStr);

		// Shall prevent that within one millisecond (accuracy of the Last-Modified header) after
		// loading additional records are added and thus records are transmitted twice or are forgotten.
		var to = LocalDateTime.now().withNano(0);

		var dataSubmissions = submissions.findAllByDepartmentIdAndMetadataLastModifiedIsBetweenOrderByMetadataLastModified(departmentId, from, to);

		var dtos = dataSubmissions.map(DataSubmissionInternalOutputDto::of).toList();

		log.debug(
			"Submission - GET from hd server: {}",
			dtos.stream().map(DataSubmissionInternalOutputDto::getRequestId).collect(Collectors.joining(", ")));

		return ResponseEntity.ok().lastModified(to.atZone(ZoneId.systemDefault())).body(dtos);
	}

//	@DeleteMapping("/hd/data-submissions")
//	void deleteDataSubmissions(@RequestParam("from") String fromStr) {
//
//		var from = LocalDateTime.parse(fromStr);
//
//		var deleteCount = submissions.deleteAllByMetadataLastModifiedIsBefore(from);
//
//		log.debug("Submission - {} submissions deleted", deleteCount);
//	}

	@DeleteMapping("/hd/data-submissions")
	void deleteDataSubmissions(@RequestParam("departmentId") DepartmentIdentifier departmentId, @RequestParam("from") String fromStr) {

		var from = LocalDateTime.parse(fromStr);

		var deleteCount = submissions.deleteAllByDepartmentIdAndMetadataLastModifiedIsBefore(departmentId, from);

		log.debug("Submission - {} submissions deleted", deleteCount);
	}

	@Data
	static class DataSubmissionInternalOutputDto {

		static DataSubmissionInternalOutputDto of(DataSubmission submission) {
			return new DataSubmissionInternalOutputDto(
				submission.getId().toString(),
				submission.getRequestId().toString(),
				submission.getDepartmentId().toString(),
				submission.getSecret(),
				submission.getKeyReferenz(),
				submission.getEncryptedData(),
				submission.getFeature());
		}

		private final String id;

		private final String requestId;

		private final String departmentId;

		private final String secret;

		private final String keyReferenz;

		private final String encryptedData;

		private final Feature feature;
	}
}
