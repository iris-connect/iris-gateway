package de.healthIMIS.iris.irislocationservice.search.db;

import de.healthIMIS.iris.irislocationservice.dto.LocationAddress;
import de.healthIMIS.iris.irislocationservice.dto.LocationContact;
import de.healthIMIS.iris.irislocationservice.dto.LocationInformation;
import de.healthIMIS.iris.irislocationservice.dto.LocationList;
import de.healthIMIS.iris.irislocationservice.search.db.model.Location;
import de.healthIMIS.iris.irislocationservice.search.db.model.LocationIdentifier;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DBSearchIndexTest {

    @Autowired
    private DBSearchIndex searchIndex;

    @Autowired
    private ModelMapper mapper;

    @Test
    public void search() {
        // When
        var location = createLocation();

        // then
        searchIndex.getRepo().saveAll(Collections.singletonList(location));

        // assert
        assertEquals(1, searchIndex.search("Zwoeinz").size());
    }

    @Test
    public void map() {
        var in = "{\n" +
                "  \"locations\": [\n" +
                "    {\n" +
                "      \"id\": \"5eddd61036d39a0ff8b11fdb\",\n" +
                "      \"name\": \"Restaurant Alberts\",\n" +
                "      \"publicKey\": \"string\",\n" +
                "      \"contact\": {\n" +
                "        \"officialName\": \"Darfichrein GmbH\",\n" +
                "        \"representative\": \"Silke \",\n" +
                "        \"address\": {\n" +
                "          \"street\": \"Türkenstr. 7\",\n" +
                "          \"city\": \"München\",\n" +
                "          \"zip\": \"80333\"\n" +
                "        },\n" +
                "        \"ownerEmail\": \"covid@restaurant.de\",\n" +
                "        \"email\": \"covid2@restaurant.de\",\n" +
                "        \"phone\": \"die bleibt privat :-)\"\n" +
                "      },\n" +
                "      \"contexts\": [\n" +
                "        {\n" +
                "          \"id\": \"5f4bfff742c1bf5f72918851\",\n" +
                "          \"name\": \"Raum 0815\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        var ll = mapper.map(in, LocationList.class);
        assertNotNull(ll.getLocations());
    }

    @Test
    public void delete() {
        
    }

    private Location createLocation() {
        var address = new LocationAddress();
        address.city("Berlin");
        address.setStreet("Quitzowstraße 121");
        address.zip("15434");

        var contact = new LocationContact();
        contact.setOwnerEmail("test@mail.com");
        contact.setEmail("test2@mail.com");
        contact.setPhone("01234567890");
        contact.setOfficialName("Foo Bar GmbH");
        contact.setAddress(address);

        var location = new LocationInformation();
        location.setName("Zwoeinz Bar");
        location.setId("ac04638c-9668-4a62-8194-d20833e6182f");
        location.setContact(contact);
        
        var location_flat = mapper.map(location, Location.class);
        location_flat.setId(new LocationIdentifier("provider-id", location.getId()));
        return location_flat;
    }


}