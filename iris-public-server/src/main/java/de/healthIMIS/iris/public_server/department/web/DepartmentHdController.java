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
package de.healthIMIS.iris.public_server.department.web;

import javax.validation.Valid;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.healthIMIS.iris.public_server.department.Department;
import de.healthIMIS.iris.public_server.department.Department.DepartmentIdentifier;
import de.healthIMIS.iris.public_server.department.DepartmentRepository;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller of the internal end-points for department informations.
 * 
 * @author Jens Kutzsche
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class DepartmentHdController {

	private final @NonNull DepartmentRepository departments;

	@PutMapping("/hd/departments/{id}")
	@ResponseStatus(HttpStatus.OK)
	DepartmentDto putDataRequest(@PathVariable("id") DepartmentIdentifier id, @Valid @RequestBody DepartmentDto payload, Errors errors) {

		var department = new Department(id, payload.getName(), payload.getKeyReferenz(), payload.key);

		try {
			departments.deleteById(department.getId());
		} catch (EmptyResultDataAccessException e) {
		}
		departments.save(department);

		log.debug(
			"Department - PUT from hd + saved: {} (Name: {}; Key-Referenz: {})",
			department.getId().toString(),
			department.getName(),
			department.getKeyReferenz());

		return payload;
	}

	@Data
	static class DepartmentDto {

		private String name;
		private String keyReferenz;
		private String key;
	}
}
