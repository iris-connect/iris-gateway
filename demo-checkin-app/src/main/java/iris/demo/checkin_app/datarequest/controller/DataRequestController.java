package iris.demo.checkin_app.datarequest.controller;

import iris.demo.checkin_app.datarequest.model.dto.LocationDataRequestDto;
import iris.demo.checkin_app.datasubmission.service.DataSubmissionService;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/iris/data-request")
@Slf4j
public class DataRequestController {

	@Autowired
	private DataSubmissionService dataSubmissionService;

	@PostMapping
	public ResponseEntity<String> postDataRequest(@Valid @RequestBody LocationDataRequestDto locationDataRequestDto) {
		var headers = new HttpHeaders();
		String result;

		try {
			result = dataSubmissionService.sendDataForRequest(locationDataRequestDto);
		} catch (Exception e) {

			log.error(e.getMessage());

			return new ResponseEntity<>("Could not send data. The error was logged.", headers, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(result, headers, HttpStatus.ACCEPTED);
	}

}
