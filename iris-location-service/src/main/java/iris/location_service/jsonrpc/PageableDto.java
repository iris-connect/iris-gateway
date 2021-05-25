package iris.location_service.jsonrpc;

import java.util.Optional;

import org.springframework.data.domain.Sort;

import lombok.*;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableDto {
	private int page;
	private int size;
	private Optional<String> sortBy;
	private Optional<Sort.Direction> direction = Optional.ofNullable(Sort.DEFAULT_DIRECTION);
}
