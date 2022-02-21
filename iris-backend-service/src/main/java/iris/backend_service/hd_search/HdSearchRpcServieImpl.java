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

		var baseAddress = hd.getAddress();
		var address = new Address(baseAddress.getStreet(), baseAddress.getZipCode(), baseAddress.getPlace());

		var contact = new ContactData(hd.getPhone(), hd.getFax(), hd.getEmail());

		var covid19ContactData = hd.getCovid19ContactData();
		var covidContact = new ContactData(covid19ContactData.getHotline(), covid19ContactData.getFax(),
				covid19ContactData.getEmail());

		var entryContactData = hd.getEinreiseContactData();
		var entryContact = new ContactData(entryContactData.getHotline(), entryContactData.getFax(),
				entryContactData.getEmail());

		return new HealthDepartmentDto(hd.getName(), hd.getCode(), epsName, hd.getDepartment(), address, contact,
				covidContact, entryContact);
	}
}
