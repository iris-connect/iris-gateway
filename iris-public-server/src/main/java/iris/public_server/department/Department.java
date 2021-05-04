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
package iris.public_server.department;

import iris.public_server.core.Aggregate;
import iris.public_server.core.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * @author Jens Kutzsche
 */
@Entity
@Table(name = "department")
@NoArgsConstructor(force = true)
@Getter
@Setter(AccessLevel.PACKAGE)
public class Department extends Aggregate<Department, Department.DepartmentIdentifier> {

	private final String name;
	private String keyReference;
	private final @Lob String publicKey;

	public Department(DepartmentIdentifier id, String name, String keyReference, String publicKey) {

		super();

		this.id = id;
		this.name = name;
		this.keyReference = keyReference;
		this.publicKey = publicKey;
	}

	@Embeddable
	@EqualsAndHashCode
	@RequiredArgsConstructor(staticName = "of")
	@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
	public static class DepartmentIdentifier implements Id, Serializable {

		private static final long serialVersionUID = -5946799883757153213L;
		final UUID departmentId;

		public static DepartmentIdentifier of(String uuid) {
			return of(UUID.fromString(uuid));
		}

		@Override
		public String toString() {
			return departmentId.toString();
		}
	}
}
