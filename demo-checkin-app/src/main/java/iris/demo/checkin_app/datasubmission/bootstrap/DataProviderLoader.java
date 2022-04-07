package iris.demo.checkin_app.datasubmission.bootstrap;

import iris.demo.checkin_app.config.ResourceHelper;
import iris.demo.checkin_app.datasubmission.model.dto.DataProviderDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RequiredArgsConstructor
@Component
public class DataProviderLoader {

	@NonNull
	private ResourceHelper resourceHelper;

	@NonNull
	private ObjectMapper objectMapper;

	@SneakyThrows
	public DataProviderDto getDataProvider() {

		var resource = resourceHelper.loadResource("classpath:bootstrap/dataprovider/smartmeeting.json");

		return objectMapper.registerModule(new JavaTimeModule()).readValue(
				resource.getInputStream(), DataProviderDto.class);
	}

}
