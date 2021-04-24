package de.healthIMIS.irisappapidemo.datasubmission.service;

import de.healthIMIS.irisappapidemo.datarequest.model.dto.LocationDataRequestDto;
import de.healthIMIS.irisappapidemo.datasubmission.bootstrap.DataProviderLoader;
import de.healthIMIS.irisappapidemo.datasubmission.bootstrap.GuestLoader;
import de.healthIMIS.irisappapidemo.datasubmission.encryption.GuestListEncryptor;
import de.healthIMIS.irisappapidemo.datasubmission.model.dto.DataProviderDto;
import de.healthIMIS.irisappapidemo.datasubmission.model.dto.GuestDto;
import de.healthIMIS.irisappapidemo.datasubmission.model.dto.GuestListDto;
import de.healthIMIS.irisappapidemo.datasubmission.model.dto.GuestSubmissionDto;
import de.healthIMIS.irisappapidemo.datasubmission.web.client.DataSubmissionClient;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Component
public class DataSubmissionService {

    @Autowired
    private GuestLoader guestLoader;

    @Autowired
    private DataProviderLoader dataProviderLoader;

    @Autowired
    private DataSubmissionClient dataSubmissionClient;

    public void sendDataForRequest(LocationDataRequestDto locationDataRequest) throws Exception {

        List<GuestDto> guests = guestLoader.getGuests();
        DataProviderDto dataProvider = dataProviderLoader.getDataProvider();
        GuestListDto guestList = GuestListDto.builder().
                guests(guests).
                additionalInformation("").
                startDate(Instant.now()).
                endDate(Instant.now().plus(6, ChronoUnit.HOURS)).
                dataProvider(dataProvider).
                build();
        GuestListEncryptor encryptor = GuestListEncryptor.builder().
                guestList(guestList).
                givenPublicKey(locationDataRequest.getKeyOfHealthDepartment()).
                build();

        GuestSubmissionDto guestSubmission = GuestSubmissionDto.builder().
                encryptedData(encryptor.encrypt()).
                keyReference(locationDataRequest.getKeyReference()).
                secret(encryptor.getSecretKeyBase64()).
                build();

        dataSubmissionClient.postDataSubmissionGuests(guestSubmission, locationDataRequest.getSubmissionUri());
    }
}
