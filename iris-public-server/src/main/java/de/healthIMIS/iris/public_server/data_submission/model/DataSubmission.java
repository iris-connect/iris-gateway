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
package de.healthIMIS.iris.public_server.data_submission.model;

import de.healthIMIS.iris.public_server.core.Feature;
import de.healthIMIS.iris.public_server.core.entity.Auditable;
import de.healthIMIS.iris.public_server.data_request.DataRequest.DataRequestIdentifier;
import de.healthIMIS.iris.public_server.department.Department.DepartmentIdentifier;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.*;

/**
 * A data submission from an app of a citizen or event/location operator to the health department.
 * 
 * @author Jens Kutzsche
 */
@Entity
@Table(name = "data_submission")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@Getter
@Setter(AccessLevel.PACKAGE)
public class DataSubmission extends Auditable {

	@Id
	@Column(name = "submission_id", columnDefinition = "BINARY(16)")
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
			name = "UUID",
			strategy = "org.hibernate.id.UUIDGenerator",
			parameters = {
					@org.hibernate.annotations.Parameter(
							name = "uuid_gen_strategy_class",
							value = "org.hibernate.id.uuid.CustomVersionFourStrategy"
					)
			}
	)
	private UUID id;

	private DataRequestIdentifier requestId;
	private DepartmentIdentifier departmentId;

	private String secret;
	private String keyReference;
	@Setter
	private LocalDateTime requested;
	private @Lob String encryptedData;

	@Enumerated(EnumType.STRING) @Column(nullable = false)
	private Feature feature;

	public DataSubmission(DataRequestIdentifier requestId, DepartmentIdentifier departmentId, String secret,
			String keyReference, String encryptedData, Feature feature) {

		super();

		this.requestId = requestId;
		this.departmentId = departmentId;
		this.secret = secret;
		this.keyReference = keyReference;
		this.encryptedData = encryptedData;
		this.feature = feature;
	}

}
