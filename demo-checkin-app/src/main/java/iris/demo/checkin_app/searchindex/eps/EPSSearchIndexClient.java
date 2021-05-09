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

import java.lang.reflect.Array;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.UUID;

@Service
@AllArgsConstructor
@Primary
@Slf4j
public class EPSSearchIndexClient implements SearchIndexClient {

	private final static String TEMPORARY_PROVIDER_ID = "demo-app";
	private final JsonRpcHttpClient rpcClient;
	private final EPSClientProperties epsClientProperties;

	@Override
	public void updateLocations(LocationsDto locationsDto) throws IRISDataSubmissionException {
		UpdateLocationsDTO updateLocationsDTO = UpdateLocationsDTO.builder()
				.providerId(TEMPORARY_PROVIDER_ID)
				.locations(locationsDto.getLocations())
				.build();
		var methodName = epsClientProperties.getDefaultLocationServiceEndpoint() + ".postLocationsToSearchIndex";
		try {
			log.info("Sent request with response "+rpcClient.invoke(methodName, updateLocationsDTO, JsonRPCStringResult.class));
		} catch (Throwable t) {
			throw new IRISDataSubmissionException(t);
		}
	}

	@Override
	public void deleteLocation(LocationDto locationDto) throws IRISDataSubmissionException {
		DeleteLocationDTO deleteLocationDTO = DeleteLocationDTO.builder()
				.providerId(TEMPORARY_PROVIDER_ID)
				.locationId(locationDto.getId().toString())
				.build();
		var methodName = epsClientProperties.getDefaultLocationServiceEndpoint() + ".deleteLocationFromSearchIndex";
		try {
			log.info("Sent request with response "+rpcClient.invoke(methodName, deleteLocationDTO, JsonRPCStringResult.class));
		} catch (Throwable t) {
			throw new IRISDataSubmissionException(t);
		}
	}
}
