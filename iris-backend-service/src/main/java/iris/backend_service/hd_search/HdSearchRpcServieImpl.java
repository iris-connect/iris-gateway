package iris.backend_service.hd_search;

import iris.backend_service.configurations.CentralConfiguration;
import iris.backend_service.hd_search.HealthDepartments.HealthDepartment;
import iris.backend_service.jsonrpc.JsonRpcClientDto;
import iris.backend_service.messages.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class HdSearchRpcServieImpl implements HdSearchRpcService {

	private final @NotNull HealthDepartments healthDepartments;
	private final @NotNull CentralConfiguration config;
	private final @NotNull AlertService alerts;

	@Override
	public List<HealthDepartmentDto> searchForHd(@Valid JsonRpcClientDto client, @NotEmpty String searchKeyword) {

		var hds = healthDepartments.findDepartmentsContains(searchKeyword);

		log.debug("JSON-RPC - Client {} searchs HD for: {} ⇒ found: {}", client.getName(), searchKeyword, hds.size());

		return hds.stream().map(this::mapToDto).toList();
	}

	private HealthDepartmentDto mapToDto(HealthDepartment hd) {

		var epsName = config.getHdNameFor(hd.getCode()).orElse(null);

		var address = new Address(hd.getAddress().getStreet(), hd.getAddress().getZipCode(), hd.getAddress().getPlace());
		var contact = new ContactData(hd.getPhone(), hd.getFax(), hd.getEmail());
		var covidContact = new ContactData(hd.getCovid19ContactData().getHotline(), hd.getCovid19ContactData().getFax(),
				hd.getCovid19ContactData().getEmail());
		var entryContact = new ContactData(hd.getEinreiseContactData().getHotline(),
				hd.getEinreiseContactData().getFax(),
				hd.getEinreiseContactData().getEmail());

		return new HealthDepartmentDto(hd.getName(), hd.getCode(), epsName, hd.getDepartment(), address, contact,
				covidContact, entryContact);
	}
}
