package iris.location_service.jsonrpc;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import iris.location_service.dto.LocationInformation;
import iris.location_service.dto.LocationOverviewDto;
import iris.location_service.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;


@AutoJsonRpcServiceImpl
@AllArgsConstructor
@Service
public class LocationRPCImpl implements LocationRPC {

    private final @NotNull LocationService locationService;

    public String postLocationsToSearchIndex( UUID providerId, List<LocationInformation> locationList
    ) {
        locationService.addLocations(providerId, locationList);
        return "OK";
    }

    @Override
    public List<LocationOverviewDto> getProviderLocations(UUID providerId) {
        return locationService.getProviderLocations(providerId);
    }

    @Override
    public String deleteLocationFromSearchIndex(UUID providerId, String locationId) {
        if (locationService.deleteLocation(providerId, locationId))
            return "OK";
        return "NOT FOUND";
    }

}
