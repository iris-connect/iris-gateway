package iris.demo.contact_diary_app.submission.rpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Events {
	List<Event> events;
	Instant startDate;
	Instant endDate;
}
