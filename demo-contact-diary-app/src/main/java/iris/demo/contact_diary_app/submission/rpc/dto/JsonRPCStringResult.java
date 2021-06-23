package iris.demo.contact_diary_app.submission.rpc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
// TODO: common code with checkin demo app
public class JsonRPCStringResult {

    @JsonProperty("_")
    private String status;

}
