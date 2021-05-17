package iris.location_service.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import iris.location_service.dto.LocationList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
class LocationIndexControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper om;

	@Test
	public void testPost() throws Exception {
		// when, then, assert
		postLocations();
	}

	@Test
	public void testSearch() throws Exception {
		postLocations();
		var res = mockMvc.perform(MockMvcRequestBuilders.get("/search/Restaurant")
				.header("x-provider-id", "f002f370-bd54-4325-ad91-1aff3bf730a5"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		var json = om.readValue(res.getResponse().getContentAsString(), LocationList.class);

		assertEquals(1, json.getLocations().size());
	}

	@Test
	public void testDelete() throws Exception {
		postLocations();

		mockMvc.perform(MockMvcRequestBuilders.delete("/search-index/locations/5eddd61036d39a0ff8b11fdb")
				.header("x-provider-id", "f002f370-bd54-4325-ad91-1aff3bf730a5"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	private void postLocations() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/search-index/locations")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestData.LOCATIONS)
				.header("x-provider-id", "f002f370-bd54-4325-ad91-1aff3bf730a5"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

}
