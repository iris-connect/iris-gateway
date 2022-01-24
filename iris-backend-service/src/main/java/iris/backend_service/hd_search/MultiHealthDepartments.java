package iris.backend_service.hd_search;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.data.util.Streamable;

/**
 * @author Jens Kutzsche
 */
public record MultiHealthDepartments(List<HealthDepartments> healthDepartments)
		implements Streamable<HealthDepartments>, HealthDepartments {

	@Override
	public Iterator<HealthDepartments> iterator() {
		return healthDepartments.iterator();
	}

	@Override
	public List<HealthDepartment> getAll() {

		return stream()
				.flatMap(it -> it.getAll().stream())
				.toList();
	}

	@Override
	public Optional<HealthDepartment> findDepartmentWithExact(String searchString) {

		return stream()
				.flatMap(it -> it.findDepartmentWithExact(searchString).stream())
				.findFirst();
	}

	@Override
	public boolean hasDepartmentWithExact(String searchString) {
		return stream().anyMatch(it -> it.hasDepartmentWithExact(searchString));
	}

	@Override
	public List<HealthDepartment> findDepartmentsContains(String searchString) {

		return stream()
				.flatMap(it -> it.findDepartmentsContains(searchString).stream())
				.toList();
	}
}
