package iris.public_server.data_request.web;

import static iris.public_server.data_request.DataRequestDataInitializer.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import iris.public_server.IrisWebIntegrationTest;
import iris.public_server.data_request.DataRequest.DataRequestIdentifier;
import iris.public_server.web.IrisLinkRelations;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.jayway.jsonpath.JsonPath;

@IrisWebIntegrationTest
@RequiredArgsConstructor
class DataRequestApiControllerIntegrationTests {

	private final MockMvc mvc;
	private final LinkDiscoverer discoverer;

	@Test
	void getDataRequestByCode_contactsEvents() throws Exception {

		var response = performeGet(REQ_ID_1, status().isOk());

		var document = JsonPath.parse(response);

		assertThat(discoverer.findLinkWithRel(IrisLinkRelations.CONTACTS_EVENTS_SUBMISSION, document.jsonString()))
				.isPresent();
		assertThat(discoverer.findLinkWithRel(IrisLinkRelations.GUESTS_SUBMISSION, document.jsonString())).isNotPresent();
		assertThat(document.read("$.healthDepartment", String.class)).isEqualTo("Gesundheitsamt Meißen");
		assertThat(document.read("$.keyOfHealthDepartment", String.class)).isEqualTo("key of HD");
		assertThat(document.read("$.keyReference", String.class)).isEqualTo("keyRef-XYZ");
		assertThat(document.read("$.start", String.class)).isNotNull();
		assertThat(document.read("$.requestDetails", String.class)).isEqualTo("requestDetails");
	}

	@Test
	void getDataRequestByCode_guests() throws Exception {

		var response = performeGet(REQ_ID_2, status().isOk());

		var document = JsonPath.parse(response);

		assertThat(discoverer.findLinkWithRel(IrisLinkRelations.GUESTS_SUBMISSION, document.jsonString())).isPresent();
		assertThat(discoverer.findLinkWithRel(IrisLinkRelations.CONTACTS_EVENTS_SUBMISSION, document.jsonString()))
				.isNotPresent();
	}

	@Test
	void getDataRequestByCode_missingCode() throws Exception {
		performeGet(DataRequestIdentifier.of(UUID.randomUUID()), status().isNotFound());
	}

	@Test
	void getDataRequestByCode_closedReq() throws Exception {

		var response = performeGet(REQ_ID_4, status().isBadRequest());

		var document = JsonPath.parse(response);

		assertThat(document.read("$", String.class)).isNotNull();
	}

	private String performeGet(DataRequestIdentifier id, ResultMatcher resultMatcher) throws Exception {
		return mvc.perform(
				get("/data-requests/{code}", id.toString()))
				.andExpect(resultMatcher)
				.andReturn().getResponse().getContentAsString();
	}
}
