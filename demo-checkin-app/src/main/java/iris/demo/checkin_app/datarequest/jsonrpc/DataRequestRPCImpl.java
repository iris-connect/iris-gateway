package iris.demo.checkin_app.datarequest.jsonrpc;

import iris.demo.checkin_app.datarequest.model.dto.LocationDataRequestDto;
import iris.demo.checkin_app.datasubmission.service.DataSubmissionService;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;

@AutoJsonRpcServiceImpl
@AllArgsConstructor
@Service
public class DataRequestRPCImpl implements DataRequestRPC {

	private final @NotNull DataSubmissionService dataSubmissionService;

	@Override
	public String createDataRequest(LocationDataRequestDto locationDataRequestDto) {
		try {
			dataSubmissionService.sendDataForRequest(locationDataRequestDto);
			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR: "+e.getMessage();
		}
	}
}
