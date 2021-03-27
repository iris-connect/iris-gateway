package de.healthIMIS.irisappapidemo.web.client;

import de.healthIMIS.irisappapidemo.searchindex.model.AddressDto;
import de.healthIMIS.irisappapidemo.searchindex.model.ContactDto;
import de.healthIMIS.irisappapidemo.searchindex.model.ContextDto;
import de.healthIMIS.irisappapidemo.searchindex.model.LocationDto;
import de.healthIMIS.irisappapidemo.searchindex.web.client.SearchIndexClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class SearchIndexClientTest {

    @Autowired
    SearchIndexClient client;

    @Test
    void testUpdateLocations() {

        ContextDto firstContext = ContextDto.builder().id(UUID.randomUUID()).name("Tisch 1").build();

        List<ContextDto> contexts = new ArrayList<>();
        contexts.add(firstContext);

        List<LocationDto> locationsDto = new ArrayList<>();
        locationsDto.add(
                LocationDto.builder().
                        id(UUID.randomUUID()).
                        name("Da Michele").
                        publicKey("Da Michele").
                        contact(
                                ContactDto.builder().
                                        officialName("SmartMeeting GmbH").
                                        email("tim@smartmeeting.online").
                                        ownerEmail("tim@smartmeeting.online").
                                        phone("0151 41800093").
                                        address(
                                                AddressDto.builder().
                                                        city("Darmstadt").
                                                        zip(64293).
                                                        street("Europaplatz 5").
                                                        build()
                                        ).
                                        build()
                        ).
                        contexts(contexts).
                        build());

        client.updateLocations(locationsDto);

    }
}