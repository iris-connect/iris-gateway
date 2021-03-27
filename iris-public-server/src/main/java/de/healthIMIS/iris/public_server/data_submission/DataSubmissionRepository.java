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

import de.healthIMIS.iris.public_server.data_submission.DataSubmission.DataSubmissionIdentifier;
import de.healthIMIS.iris.public_server.department.Department.DepartmentIdentifier;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

/**
 * @author Jens Kutzsche
 */
public interface DataSubmissionRepository extends CrudRepository<DataSubmission, DataSubmissionIdentifier> {

	@Transactional
	Streamable<DataSubmission> findAllByMetadataLastModifiedIsBetweenOrderByMetadataLastModified(LocalDateTime from,
			LocalDateTime to);

	@Transactional
	int deleteAllByMetadataLastModifiedIsBefore(LocalDateTime lastSync);

	@Transactional
	Streamable<DataSubmission> findAllByDepartmentIdAndMetadataLastModifiedIsBetweenOrderByMetadataLastModified(
			DepartmentIdentifier id, LocalDateTime from, LocalDateTime to);

	@Transactional
	int deleteAllByDepartmentIdAndMetadataLastModifiedIsBefore(DepartmentIdentifier id, LocalDateTime lastSync);
}
