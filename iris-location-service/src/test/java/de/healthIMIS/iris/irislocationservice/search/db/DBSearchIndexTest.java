package de.healthIMIS.iris.irislocationservice.search.db;

import de.healthIMIS.iris.irislocationservice.dto.LocationAddress;
import de.healthIMIS.iris.irislocationservice.dto.LocationContact;
import de.healthIMIS.iris.irislocationservice.dto.LocationInformation;
import de.healthIMIS.iris.irislocationservice.search.LocationSearchEntry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DBSearchIndexTest {

    @Autowired
    private DBSearchIndex searchIndex;

    @Test
    public void search() {
        // When
        var location = createLocation();

        // then
        searchIndex.put(Collections.singletonList(location));

        // assert
        assertEquals(1, searchIndex.search("Zwoeinz").size());
    }

    @Test
    public void delete() {

    }

    private LocationSearchEntry createLocation() {
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

        return new LocationSearchEntry("providerId", location);
    }


}