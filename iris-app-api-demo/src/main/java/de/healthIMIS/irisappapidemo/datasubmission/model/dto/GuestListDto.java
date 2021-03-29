package de.healthIMIS.irisappapidemo.datasubmission.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestListDto {

    private List<GuestDto> guests;

    private DataProviderDto dataProvider;

    private String additionalInformation;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
