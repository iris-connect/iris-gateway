package de.healthIMIS.iris.irislocationservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.healthIMIS.iris.irislocationservice.dto.LocationList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        var json = om.readValue(res.getResponse().getContentAsString(), LocationList.class);

        assertEquals(1, json.getLocations().size());
    }

    @Test
    public void testDelete() throws Exception {
        postLocations();

        mockMvc.perform(MockMvcRequestBuilders.delete("/search-index/5eddd61036d39a0ff8b11fdb"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    private void postLocations() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/search-index/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestData.LOCATIONS)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

}