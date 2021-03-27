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
package de.healthIMIS.iris.public_server.web;

import org.springframework.hateoas.LinkRelation;

/**
 * @author Jens Kutzsche
 */
public interface IrisLinkRelations {

	LinkRelation REQUEST_BY_CODE = LinkRelation.of("GetDataRequestByCode");

	LinkRelation LOCATIONS_TO_SEARCH_INDEX = LinkRelation.of("PutLocationsToSearchIndex");
	LinkRelation DELETE_LOCATIONS = LinkRelation.of("DeleteLocationsFromSearchIndex");

	LinkRelation CONTACTS_EVENTS_SUBMISSION = LinkRelation.of("PostContactsEventsSubmission");
	LinkRelation GUESTS_SUBMISSION = LinkRelation.of("PostGuestsSubmission");
}
