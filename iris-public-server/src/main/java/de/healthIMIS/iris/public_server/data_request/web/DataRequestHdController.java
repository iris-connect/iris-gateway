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

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import de.healthIMIS.iris.public_server.config.AppProviderConfiguration;
import de.healthIMIS.iris.public_server.core.Feature;
import de.healthIMIS.iris.public_server.data_request.DataRequest;
import de.healthIMIS.iris.public_server.data_request.DataRequest.DataRequestIdentifier;
import de.healthIMIS.iris.public_server.data_request.DataRequest.Status;
import de.healthIMIS.iris.public_server.data_request.DataRequestRepository;
import de.healthIMIS.iris.public_server.data_submission.web.DataSubmissionApi;
import de.healthIMIS.iris.public_server.department.Department.DepartmentIdentifier;
import de.healthIMIS.iris.public_server.department.DepartmentRepository;
import java.net.URI;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.UUID;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Controller of the internal end-points for health department site to exchange data requests.
 *
 * @author Jens Kutzsche
 */
@RestController
@Slf4j
@AllArgsConstructor
public class DataRequestHdController {

  private final @NonNull RestTemplate rest;
  private final @NonNull DataRequestRepository requests;
  private final @NonNull DepartmentRepository departments;
  private final @NonNull AppProviderConfiguration appProviderConfiguration;

  @PutMapping("/hd/data-requests/{id}")
  @ResponseStatus(HttpStatus.OK)
  void putDataRequest(
      @PathVariable("id") DataRequestIdentifier id,
      @Valid @RequestBody DataRequestInternalInputDto payload,
      Errors errors) {

    var dataRequest = saveOrUpdate(id, payload);

    if (isNotEmpty(payload.providerId) && isNotEmpty(payload.locationId)) {

      var appProviderDataRequest =
          getAppProviderPayloadForDataRequest(dataRequest, payload.locationId);

      // do request to app server
      var appConfig = appProviderConfiguration.findByProviderId(payload.providerId);
      var url = appConfig.getDataRequestEndpoint();
      var res = rest.postForEntity(url, appProviderDataRequest, String.class);
      if (res.getStatusCode() != HttpStatus.ACCEPTED) {
        // TODO use string templating
        var msg = "Unexpected AppServer Status Code " + res.getStatusCode().toString();
        log.error(msg);
        // TODO introduce global error handling
        throw new RuntimeException(msg);
      }
    }

    log.debug("Request - PUT from hd server + saved: {}", dataRequest.getId().toString());
  }

  private AppProviderDataRequestDTO getAppProviderPayloadForDataRequest(
      DataRequest dataRequest, String locationId) {
    var appServerRequestPayload = new AppProviderDataRequestDTO();
    appServerRequestPayload.setHealthDepartment(dataRequest.getDepartmentId().toString());
    appServerRequestPayload.setRequestDetails(dataRequest.getRequestDetails());
    appServerRequestPayload.setLocationId(locationId);
    appServerRequestPayload.setStart(dataRequest.getRequestStart().atOffset(ZoneOffset.UTC));
    appServerRequestPayload.setEnd(dataRequest.getRequestEnd().atOffset(ZoneOffset.UTC));

    URI uri =
        linkTo(methodOn(DataSubmissionApi.class).postGuestsSubmission(dataRequest.getId(), null))
            .toUri();
    appServerRequestPayload.setSubmissionUri(uri.toString());

    var department = departments.findById(dataRequest.getDepartmentId());
    if (department.isEmpty()) {
      var msg = "No department found for id " + dataRequest.getDepartmentId().toString();
      log.error(msg);
      // TODO introduce global error handling
      throw new RuntimeException(msg);
    }

    appServerRequestPayload.setKeyOfHealthDepartment(department.get().getPublicKey());
    appServerRequestPayload.setKeyReference(department.get().getKeyReference());
    return appServerRequestPayload;
  }

  private DataRequest saveOrUpdate(DataRequestIdentifier id, DataRequestInternalInputDto payload) {
    var existingDataRequest = requests.findById(id);
    existingDataRequest.ifPresent(dataRequest -> requests.deleteById(dataRequest.getId()));

    var dataRequest =
        new DataRequest(
            id,
            DepartmentIdentifier.of(payload.departmentId),
            payload.requestStart,
            payload.requestEnd,
            payload.getRequestDetails(),
            payload.features,
            payload.status);
    return requests.save(dataRequest);
  }

  @Data
  static class AppProviderDataRequestDTO {
    String healthDepartment;
    String keyOfHealthDepartment;
    String keyReference;
    OffsetDateTime start;
    OffsetDateTime end;
    String requestDetails;
    String submissionUri;
    String locationId;
  }

  // TODO use generated class
  @Data
  static class DataRequestInternalInputDto {

    private UUID departmentId;

    private String locationId;
    private String providerId;

    private Instant requestStart;
    private Instant requestEnd;

    private String requestDetails;

    // these two seem to not be used
    private Set<Feature> features;
    private Status status; // TODO need more status?
  }
}
