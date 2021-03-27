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
package de.healthIMIS.iris.public_server.data_submission;

import de.healthIMIS.iris.public_server.core.DataInitializer;
import de.healthIMIS.iris.public_server.core.Feature;
import de.healthIMIS.iris.public_server.data_request.DataRequestDataInitializer;
import de.healthIMIS.iris.public_server.department.DepartmentDataInitializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DataSubmissionDataInitializer implements DataInitializer {

	private final DataSubmissionRepository submissions;

	/*
	 * (non-Javadoc)
	 * @see quarano.core.DataInitializer#initialize()
	 */
	@Override
	public void initialize() {

		log.debug("Test data: creating data submissions …");

		var list = new ArrayList<DataSubmission>();

		list.add(new DataSubmission(DataRequestDataInitializer.REQ_ID_1, DepartmentDataInitializer.DEPARTMENT_ID_1, "salt",
				"key", "DATA OF CONTACTS", Feature.Contacts_Events));

		list.add(new DataSubmission(DataRequestDataInitializer.REQ_ID_1, DepartmentDataInitializer.DEPARTMENT_ID_1, "salt",
				"key", "DATA OF EVENTS", Feature.Contacts_Events));

		submissions.saveAll(list);
	}
}
