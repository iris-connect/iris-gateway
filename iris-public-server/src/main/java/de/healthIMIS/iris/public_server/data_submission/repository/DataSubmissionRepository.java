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
package de.healthIMIS.iris.public_server.data_submission.repository;

import de.healthIMIS.iris.public_server.data_request.DataRequest;
import de.healthIMIS.iris.public_server.data_submission.model.DataSubmission;
import de.healthIMIS.iris.public_server.department.Department.DepartmentIdentifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * @author Jens Kutzsche
 */
public interface DataSubmissionRepository extends CrudRepository<DataSubmission, DataSubmission.DataSubmissionIdentifier> {

    @Transactional
    Streamable<DataSubmission> findAllByDepartmentIdAndMetadataLastModifiedIsAfter(
            DepartmentIdentifier id, Instant from);

    @Transactional
    Streamable<DataSubmission> findAllByDepartmentIdAndRequestedAtIsBefore(
            DepartmentIdentifier departmentIdentifier,
            Instant searchDate);

    @Transactional
    Streamable<DataSubmission> findAllByRequestedAtBefore(Instant time);
}
