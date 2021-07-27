package iris.demo.checkin_app.searchindex.eps;

import iris.demo.checkin_app.config.EPSClientProperties;
import iris.demo.checkin_app.datasubmission.exceptions.IRISDataSubmissionException;
import iris.demo.checkin_app.searchindex.SearchIndexClient;
import iris.demo.checkin_app.searchindex.eps.dto.DeleteLocationDTO;
import iris.demo.checkin_app.searchindex.eps.dto.JsonRPCStringResult;
import iris.demo.checkin_app.searchindex.eps.dto.UpdateLocationsDTO;
import iris.demo.checkin_app.searchindex.model.LocationDto;
import iris.demo.checkin_app.searchindex.model.LocationsDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

@Service
@AllArgsConstructor
@Primary
@Slf4j
public class EPSSearchIndexClient implements SearchIndexClient {

	private final JsonRpcHttpClient rpcClient;
	private final EPSClientProperties epsClientProperties;

	@Override
	public void updateLocations(LocationsDto locationsDto) throws IRISDataSubmissionException {
		UpdateLocationsDTO updateLocationsDTO = UpdateLocationsDTO.builder()
				.locations(locationsDto.getLocations())
				.build();
		var methodName = epsClientProperties.getBackendServiceEndpoint() + ".postLocationsToSearchIndex";
		try {
			log.info(
					"Sent request with response " + rpcClient.invoke(methodName, updateLocationsDTO, JsonRPCStringResult.class));
		} catch (Throwable t) {
			throw new IRISDataSubmissionException(t);
		}
	}

	@Override
	public void deleteLocation(LocationDto locationDto) throws IRISDataSubmissionException {
		DeleteLocationDTO deleteLocationDTO = DeleteLocationDTO.builder()
				.locationId(locationDto.getId().toString())
				.build();
		var methodName = epsClientProperties.getBackendServiceEndpoint() + ".deleteLocationFromSearchIndex";
		try {
			log.info(
					"Sent request with response " + rpcClient.invoke(methodName, deleteLocationDTO, JsonRPCStringResult.class));
		} catch (Throwable t) {
			throw new IRISDataSubmissionException(t);
		}
	}
}
