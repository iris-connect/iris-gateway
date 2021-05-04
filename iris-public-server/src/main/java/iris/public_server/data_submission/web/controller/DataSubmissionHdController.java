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
package iris.public_server.data_submission.web.controller;

import iris.public_server.core.Feature;
import iris.public_server.data_submission.model.DataSubmission;
import iris.public_server.data_submission.model.DataSubmission.DataSubmissionIdentifier;
import iris.public_server.data_submission.repository.DataSubmissionRepository;
import iris.public_server.data_submission.service.DataSubmissionService;
import iris.public_server.department.Department.DepartmentIdentifier;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

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

    private final @NonNull DataSubmissionService dataSubmissionService;

    @GetMapping("/hd/data-submissions")
    HttpEntity<List<DataSubmissionInternalOutputDto>> getDataSubmissions(
            @RequestParam("departmentId") DepartmentIdentifier departmentId) {

        var dataSubmissions = dataSubmissionService.getSubmissionsForDepartmentFrom(departmentId);

        var dtos = dataSubmissions.map(DataSubmissionInternalOutputDto::of).toList();

        log.debug("Submission - GET from hd client: {}",
                dtos.stream().map(DataSubmissionInternalOutputDto::getRequestId).collect(Collectors.joining(", ")));

        return ResponseEntity.ok().body(dtos);
    }

    @DeleteMapping("/hd/data-submissions/{dataSubmissionId}")
    void deleteDataSubmissions(@PathVariable("dataSubmissionId") DataSubmissionIdentifier dataSubmissionId) {

        dataSubmissionService.deleteDataSubmissionById(dataSubmissionId);
    }

    @Data
    static class DataSubmissionInternalOutputDto {

        private final String id;
        private final String requestId;
        private final String departmentId;
        private final String secret;
        private final String keyReference;
        private final String encryptedData;
        private final Feature feature;

        static DataSubmissionInternalOutputDto of(DataSubmission submission) {
        	return new DataSubmissionInternalOutputDto(
    					submission.getId().toString(),
    					submission.getRequestId().toString(),
    					submission.getDepartmentId().toString(),
    					submission.getSecret(),
    					submission.getKeyReference(),
    					submission.getEncryptedData(),
    					submission.getFeature());
        }
    }
}
