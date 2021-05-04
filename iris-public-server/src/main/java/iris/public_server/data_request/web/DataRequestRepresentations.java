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
package iris.public_server.data_request.web;

import static iris.public_server.web.IrisLinkRelations.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import iris.public_server.core.Feature;
import iris.public_server.data_request.DataRequest;
import iris.public_server.data_submission.web.controller.DataSubmissionApi;
import iris.public_server.department.DepartmentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Links;
import org.springframework.stereotype.Component;

/**
 * Responsible for the REST representations
 * 
 * @author Jens Kutzsche
 */
@Component
@RequiredArgsConstructor
public class DataRequestRepresentations {

	private final @NonNull DepartmentRepository departments;

	public EntityModel<DataRequestDto> toRepresentation(DataRequest dataRequest) {

		var request = new DataRequestDto()
				.start(dataRequest.getRequestStart())
				.end(dataRequest.getRequestEnd())
				.requestDetails(dataRequest.getRequestDetails());

		var departmentOpt = departments.findById(dataRequest.getDepartmentId());
		if (departmentOpt.isPresent()) {

			var department = departmentOpt.get();

			request = request.healthDepartment(department.getName());
			request = request.keyOfHealthDepartment(department.getPublicKey());
			request = request.keyReference(department.getKeyReference());
		}

		var model = EntityModel.of(request);

		var links = Links
				.of(linkTo(methodOn(DataRequestApiController.class).getDataRequestByCode(dataRequest.getId())).withSelfRel())
				.andIf(dataRequest.getFeatures().contains(Feature.Contacts_Events),
						linkTo(methodOn(DataSubmissionApi.class).postContactsEventsSubmission(dataRequest.getId(), null))
								.withRel(CONTACTS_EVENTS_SUBMISSION).withTitle("Contacts and Events"))
				.andIf(dataRequest.getFeatures().contains(Feature.Guests),
						linkTo(methodOn(DataSubmissionApi.class).postGuestsSubmission(dataRequest.getId(), null))
								.withRel(GUESTS_SUBMISSION).withTitle("Guests"));

		return model.add(links);
	}
}
