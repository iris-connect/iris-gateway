package iris.backend_service.locations.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * LocationInformation
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
        date = "2021-03-27T12:26:11.318Z[GMT]")

@NoArgsConstructor
@Getter
@Setter
public class LocationInformation {

    @NotBlank
    String id = null;

    @NotBlank
    String providerId = null;

    @NotBlank
    String name = null;

    String publicKey = null;

    @NotNull
    @Valid
    LocationContact contact = null;

    List<LocationContext> contexts = null;

}
