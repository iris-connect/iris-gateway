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
package de.healthIMIS.iris.public_server.data_submission.web.controller;

import de.healthIMIS.iris.public_server.core.Feature;
import de.healthIMIS.iris.public_server.data_submission.model.DataSubmission;
import de.healthIMIS.iris.public_server.data_submission.repository.DataSubmissionRepository;
import de.healthIMIS.iris.public_server.data_submission.service.DataSubmissionService;
import de.healthIMIS.iris.public_server.department.Department.DepartmentIdentifier;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam("departmentId") DepartmentIdentifier departmentId, @RequestParam("from") String fromStr) {

        var from = LocalDateTime.parse(fromStr);

        // Shall prevent that within one millisecond (accuracy of the Last-Modified header) after
        // loading additional records are added and thus records are transmitted twice or are forgotten.
        var to = LocalDateTime.now().withNano(0);

        var dataSubmissions = submissions
                .findAllByDepartmentIdAndMetadataLastModifiedIsBetweenOrderByMetadataLastModified(departmentId, from, to);

        var dtos = dataSubmissions.map(DataSubmissionInternalOutputDto::of).toList();

        log.debug("Submission - GET from hd client: {}",
                dtos.stream().map(DataSubmissionInternalOutputDto::getRequestId).collect(Collectors.joining(", ")));

        return ResponseEntity.ok().lastModified(to.atZone(ZoneId.systemDefault())).body(dtos);
    }

    @DeleteMapping("/hd/data-submissions/{dataSubmissionId}")
    void deleteDataSubmissions(@PathVariable("dataSubmissionId") UUID dataSubmissionId) {

        var deleteCount = dataSubmissionService.deleteDataSubmissionById(DataSubmission.DataSubmissionIdentifier.of(dataSubmissionId));

        log.debug("Submission - {} submissions deleted", deleteCount);
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
