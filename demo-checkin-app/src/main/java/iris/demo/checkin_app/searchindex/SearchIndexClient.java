package iris.demo.checkin_app.searchindex;

import iris.demo.checkin_app.datasubmission.exceptions.IRISDataSubmissionException;
import iris.demo.checkin_app.searchindex.model.LocationDto;
import iris.demo.checkin_app.searchindex.model.LocationsDto;

public interface SearchIndexClient {

	void updateLocations(LocationsDto locationsDto) throws IRISDataSubmissionException;

	void deleteLocation(LocationDto locationDto) throws IRISDataSubmissionException;

}
