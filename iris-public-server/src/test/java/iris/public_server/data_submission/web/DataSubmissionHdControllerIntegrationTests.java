package iris.public_server.data_submission.web;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import iris.public_server.DataInitializer;
import iris.public_server.DepartmentDataInitializer;
import iris.public_server.IrisWebIntegrationTest;
import iris.public_server.department.Department.DepartmentIdentifier;
import lombok.RequiredArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
		var from = Instant.now().minus(1, ChronoUnit.DAYS);

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

		// Wait for Maintenance Job
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {}

		response = getSubmissions(departmentId, from);
		document = JsonPath.parse(response);
		assertThat(document.read("$..requestId", String[].class)).isEmpty();

	}

	@Test
	void getSubmissions_noneForUnknownDepartment() throws Exception {

		var from = Instant.now().minus(1, ChronoUnit.DAYS);

		var response = getSubmissions(DepartmentIdentifier.of(UUID.randomUUID()), from);

		var document = JsonPath.parse(response);

		assertThat(document.read("$..requestId", String[].class)).isEmpty();
	}

	private String getSubmissions(DepartmentIdentifier departmentId, Instant from)
			throws UnsupportedEncodingException, Exception {

		return mvc.perform(
				get("/hd/data-submissions?departmentId={depId}&from={from}",
						departmentId, from.toString()))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
	}
}
