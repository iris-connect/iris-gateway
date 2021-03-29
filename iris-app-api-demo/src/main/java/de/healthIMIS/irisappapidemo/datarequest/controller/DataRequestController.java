package de.healthIMIS.irisappapidemo.datarequest.controller;

import de.healthIMIS.irisappapidemo.datarequest.model.dto.LocationDataRequestDto;
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

    @PostMapping
    public ResponseEntity postDataRequest(@Valid @RequestBody LocationDataRequestDto locationDataRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity(headers, HttpStatus.ACCEPTED);
    }


}
