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
package iris.public_server.web;

import static iris.public_server.web.IrisLinkRelations.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.*;

import iris.public_server.data_request.web.DataRequestApi;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.hal.HalModelBuilder;
import org.springframework.hateoas.server.mvc.MvcLink;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the entry point to the IRIS API
 * 
 * @author Jens Kutzsche
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
		date = "2021-02-18T08:11:24.698Z[GMT]")
@RestController
public class DefaultApiController implements DefaultApi {

	@Override
	public RepresentationModel<?> rootGet() {

		Links links = Links.of(MvcLink.of(on(DefaultApi.class).rootGet(), IanaLinkRelations.SELF))
				.and(linkTo(methodOn(DataRequestApi.class).getDataRequestByCode(null)).withRel(REQUEST_BY_CODE));

		return HalModelBuilder.emptyHalModel().links(links).build();
	}
}
