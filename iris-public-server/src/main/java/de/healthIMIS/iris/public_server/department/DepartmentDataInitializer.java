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
package de.healthIMIS.iris.public_server.department;

import de.healthIMIS.iris.public_server.department.Department.DepartmentIdentifier;

import java.util.UUID;

public class DepartmentDataInitializer {

	public static final DepartmentIdentifier DEPARTMENT_ID_1 = DepartmentIdentifier
			.of(UUID.fromString("a04d2e43-3d1a-464e-9926-e190ccf2dd03"));
	public static final DepartmentIdentifier DEPARTMENT_ID_2 = DepartmentIdentifier
			.of(UUID.fromString("6afbbe9b-938c-46d7-93e4-7c9e1f737273"));
}
