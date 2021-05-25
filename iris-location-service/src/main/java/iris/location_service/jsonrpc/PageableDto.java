package iris.location_service.jsonrpc;

import org.springframework.data.domain.Sort;

import lombok.*;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableDto {
	private int page;
	private int size;
	private String sortBy;
	private Sort.Direction direction = Sort.DEFAULT_DIRECTION;
}
