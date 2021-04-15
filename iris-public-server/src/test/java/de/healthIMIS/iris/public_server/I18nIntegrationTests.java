package de.healthIMIS.iris.public_server;

import static de.healthIMIS.iris.public_server.data_request.DataRequestDataInitializer.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.healthIMIS.iris.public_server.data_request.DataRequest.DataRequestIdentifier;
import lombok.RequiredArgsConstructor;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;

@IrisWebIntegrationTest
@RequiredArgsConstructor
class I18nIntegrationTests {

	private final MockMvc mvc;

	@Test
	void errorsInGerman() throws Exception {

		var response = performeGet(REQ_ID_4, Locale.GERMAN);

		var document = JsonPath.parse(response);

		assertThat(document.read("$", String.class)).contains("Gesundheitsamt");
	}

	@Test
	void errorsInEnglish() throws Exception {

		var response = performeGet(REQ_ID_4, Locale.ENGLISH);

		var document = JsonPath.parse(response);

		assertThat(document.read("$", String.class)).contains("health department");
	}

	private String performeGet(DataRequestIdentifier id, Locale locale) throws Exception {
		return mvc.perform(
				get("/data-requests/{code}", id.toString())
						.locale(locale))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
	}
}
