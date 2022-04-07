package iris.demo.checkin_app.searchindex.eps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class JsonRPCStringResult {

	@JsonProperty("_")
	private String status;

}
