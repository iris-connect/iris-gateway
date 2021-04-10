package de.healthIMIS.iris.public_server.data_submission.web;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.healthIMIS.iris.public_server.DepartmentDataInitializer;
import de.healthIMIS.iris.public_server.IrisWebIntegrationTest;
import de.healthIMIS.iris.public_server.department.Department.DepartmentIdentifier;
import lombok.RequiredArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;

@IrisWebIntegrationTest
@RequiredArgsConstructor
class DataSubmissionHdControllerIntegrationTests {

	private final MockMvc mvc;

	/**
	 * Prevents the tests from running too quickly after the data is created and thus ignoring the data.
	 */
	@BeforeAll
	void waitASec() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
	}

	@Test
	void getAndDeleteSubmissions() throws Exception {

		var departmentId = DepartmentDataInitializer.DEPARTMENT_ID_1;
		var from = LocalDateTime.now().minusDays(1);

		var response = getSubmissions(departmentId, from);
		var document = JsonPath.parse(response);
		assertThat(document.read("$..requestId", String[].class)).hasSize(3);

		response = getSubmissions(DepartmentDataInitializer.DEPARTMENT_ID_2, from);
		document = JsonPath.parse(response);
		assertThat(document.read("$..requestId", String[].class)).hasSize(1);

		mvc.perform(
				delete("/hd/data-submissions/"+document.read("$[0].id")))
				.andExpect(status().isOk());

		response = getSubmissions(DepartmentDataInitializer.DEPARTMENT_ID_2, from);
		document = JsonPath.parse(response);
		assertThat(document.read("$..requestId", String[].class)).isEmpty();
	}

	@Test
	void getAndDeleteNonDeletedSubmissions() throws Exception {

		var departmentId = DepartmentDataInitializer.DEPARTMENT_ID_1;
		var from = LocalDateTime.now().minusDays(1);

		var response = getSubmissions(departmentId, from);
		var document = JsonPath.parse(response);
		assertThat(document.read("$..requestId", String[].class)).hasSize(3);

		// Wait till suggestions will be recognized as requested but not deleted
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}

		mvc.perform(
				delete("/hd/data-submissions/"+document.read("$[2].id")))
				.andExpect(status().isOk());

		response = getSubmissions(departmentId, from);
		document = JsonPath.parse(response);
		assertThat(document.read("$..requestId", String[].class)).hasSize(2);

		// Wait till suggestions will be recognized as requested but not deleted
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {}

		mvc.perform(
				delete("/hd/data-submissions/"+document.read("$[1].id")))
				.andExpect(status().isOk());

		response = getSubmissions(departmentId, from);
		document = JsonPath.parse(response);
		assertThat(document.read("$..requestId", String[].class)).isEmpty();
	}

	@Test
	void getSubmissions_noneForNewerThenNow() throws Exception {

		var response = getSubmissions(DepartmentDataInitializer.DEPARTMENT_ID_1, LocalDateTime.now());

		var document = JsonPath.parse(response);

		assertThat(document.read("$..requestId", String[].class)).isEmpty();
	}

	@Test
	void getSubmissions_noneForUnknownDepartment() throws Exception {

		var response = getSubmissions(DepartmentIdentifier.of(UUID.randomUUID()), LocalDateTime.now().minusDays(1));

		var document = JsonPath.parse(response);

		assertThat(document.read("$..requestId", String[].class)).isEmpty();
	}

	private String getSubmissions(DepartmentIdentifier departmentId, LocalDateTime from)
			throws UnsupportedEncodingException, Exception {

		return mvc.perform(
				get("/hd/data-submissions?departmentId={depId}&from={from}",
						departmentId, from))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
	}
}
