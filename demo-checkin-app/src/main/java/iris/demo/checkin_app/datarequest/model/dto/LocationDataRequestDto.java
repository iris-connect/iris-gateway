package iris.demo.checkin_app.datarequest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDataRequestDto {

    @NotNull
    private String healthDepartment;

    @NotNull
    private String keyOfHealthDepartment;

    @NotNull
    private Instant start;

    @NotNull
    private Instant end;

    private String requestDetails;

    @NotNull
    private String submissionUri;

    @NotNull
    private String locationId;

    private String hdEndpoint;

    /**
     * Reference id of the given key.
     * This reference must be included in the submission in order to identify the
     * correct private key for decryption at the health department.
     */
    private String keyReference;

}
