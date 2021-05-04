package iris.demo.checkin_app.searchindex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationsDto {

    private List<LocationDto> locations;

    @JsonIgnore
    public Integer getCount() {
        return locations.size();
    }

    public LocationDto getLocationByIndex(Integer index) {
        return locations.get(index);
    }
}
