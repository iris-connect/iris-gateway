package de.healthIMIS.irisappapidemo.datasubmission.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class GuestSubmissionDto {

    private String secret;

    private String keyReference;

    private String encryptedData;

}
