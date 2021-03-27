package de.healthIMIS.irisappapidemo.searchindex.bootstrap;

import de.healthIMIS.irisappapidemo.searchindex.model.AddressDto;
import de.healthIMIS.irisappapidemo.searchindex.model.ContactDto;
import de.healthIMIS.irisappapidemo.searchindex.model.ContextDto;
import de.healthIMIS.irisappapidemo.searchindex.model.LocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by jt on 2019-05-17.
 */
@RequiredArgsConstructor
@Component
public class LocationsLoader {

    public List<LocationDto> getDemoLocations() {
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
                                        phone("0151 47110815").
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

        ContextDto secondContext = ContextDto.builder().id(UUID.randomUUID()).name("Außenbereich").build();
        ContextDto thirdContext = ContextDto.builder().id(UUID.randomUUID()).name("Innenbereich").build();

        List<ContextDto> secondContexts = new ArrayList<>();
        contexts.add(secondContext);
        contexts.add(thirdContext);

        locationsDto.add(
                LocationDto.builder().
                        id(UUID.randomUUID()).
                        name("Da Tim").
                        publicKey("Da Tim").
                        contact(
                                ContactDto.builder().
                                        officialName("SmartMeeting GmbH").
                                        email("tim@smartmeeting.online").
                                        ownerEmail("tim@smartmeeting.online").
                                        phone("0151 47110815").
                                        address(
                                                AddressDto.builder().
                                                        city("Köln").
                                                        zip(50933 ).
                                                        street("Aachenerstraße 999").
                                                        build()
                                        ).
                                        build()
                        ).
                        contexts(contexts).
                        build());

        return locationsDto;
    }
}