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
package iris.backend_service.hd_search;

import static org.assertj.core.api.Assertions.*;

import iris.backend_service.hd_search.HealthDepartments.HealthDepartment;
import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

/**
 * @author Jens Kutzsche
 */
@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("dev_env")
@RequiredArgsConstructor
class HealthDepartmentsTest {

	private final HealthDepartments healthDepartments;

	@Test
	void allHdsMoreThan400() {
		assertThat(healthDepartments.getAll()).hasSizeGreaterThan(400);
	}

	@Test
	void findLKMeißenByZipCode() {

		assertThat(healthDepartments.findDepartmentWithExact("01665"))
				.isPresent()
				.map(HealthDepartment::getName)
				.contains("Landkreis Meißen");
	}

	@Test
	void findLKMeißenByNamePart() {

		assertThat(healthDepartments.findDepartmentsContains("Mei"))
				.isNotEmpty()
				.map(HealthDepartment::getName)
				.contains("Landkreis Meißen");
	}
}
