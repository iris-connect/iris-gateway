package iris.public_server.web;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import iris.public_server.IrisWebIntegrationTest;
import iris.public_server.web.IrisLinkRelations;
import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;

@IrisWebIntegrationTest
@RequiredArgsConstructor
class DefaultApiControllerIntegrationTests {

	private final MockMvc mvc;
	private final LinkDiscoverer discoverer;

	@Test
	void getLinksFromRootUrl() throws Exception {

		var response = mvc.perform(get("/"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		var document = JsonPath.parse(response);

		assertThat(discoverer.findLinkWithRel(IrisLinkRelations.REQUEST_BY_CODE, document.jsonString())).isPresent();
	}
}
