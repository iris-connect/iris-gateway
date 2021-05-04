package de.healthIMIS.irisappapidemo.datarequest.controller;

import de.healthIMIS.irisappapidemo.datarequest.model.dto.LocationDataRequestDto;
import de.healthIMIS.irisappapidemo.datasubmission.service.DataSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/iris/data-request")
public class DataRequestController {

    @Autowired
    private DataSubmissionService dataSubmissionService;

    @PostMapping
    public ResponseEntity postDataRequest(@Valid @RequestBody LocationDataRequestDto locationDataRequestDto) {
        HttpHeaders headers = new HttpHeaders();

        try {
            dataSubmissionService.sendDataForRequest(locationDataRequestDto);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), headers, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(headers, HttpStatus.ACCEPTED);
    }


}
