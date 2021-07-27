package iris.backend_service.alerts;

import iris.backend_service.jsonrpc.JsonRpcClientDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
class AlertRPCImpl implements AlertRPC {

	private final AlertRepository alertRepo;
	private final ModelMapper mapper;

	@Override
	public String postAlerts(@Valid JsonRpcClientDto client, List<AlertDto> alertDtos) {

		log.trace("Alert - JSON-RPC - Post alert invoked for client: {} with {} alerts", client.getName(),
				alertDtos.size());

		var alerts = alertDtos.stream()
				.map(it -> mapper.map(it, Alert.class))
				.peek(it -> it.setClient(client.getName()))
				.collect(Collectors.toList());

		try {

			alertRepo.saveAllAndFlush(alerts);

			log.debug("Alert - JSON-RPC - Post alert for client: {} => result: OK", client.getName());

			return "OK";

		} catch (Exception e) {

			log.error(String.format("Alert - JSON-RPC - Can't save alert for client: %s => result: ", client.getName()), e);

			return "ERROR";
		}
	}
}
