package iris.demo.checkin_app.searchindex;

import iris.demo.checkin_app.datasubmission.exceptions.IRISDataSubmissionException;
import iris.demo.checkin_app.searchindex.model.LocationDto;
import iris.demo.checkin_app.searchindex.model.LocationsDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

public interface SearchIndexClient {

    public void updateLocations(LocationsDto locationsDto) throws IRISDataSubmissionException;

    public void deleteLocation(LocationDto locationDto) throws IRISDataSubmissionException;

}
