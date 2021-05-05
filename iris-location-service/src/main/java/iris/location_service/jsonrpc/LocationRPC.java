package iris.location_service.jsonrpc;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import iris.location_service.dto.LocationOverviewDto;
import iris.location_service.dto.LocationInformation;

import java.util.List;
import java.util.UUID;

@JsonRpcService("/location-rpc")
public interface LocationRPC {
    String postLocationsToSearchIndex(
            @JsonRpcParam(value = "providerId") UUID providerId,
            @JsonRpcParam(value = "locations") List<LocationInformation> locationList
    );

    List<LocationOverviewDto> getProviderLocations(@JsonRpcParam(value = "providerId") UUID providerId);

    String deleteLocationFromSearchIndex(
            @JsonRpcParam(value = "providerId") UUID providerId,
            @JsonRpcParam(value = "locationId") String locationId);
}
