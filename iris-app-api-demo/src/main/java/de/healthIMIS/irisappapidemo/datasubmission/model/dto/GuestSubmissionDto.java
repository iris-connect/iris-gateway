package de.healthIMIS.irisappapidemo.datasubmission.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GuestSubmissionDto {

    private String secret;

    private String keyReference;

    private String encryptedData;

}
