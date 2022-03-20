package iris.backend_service.hd_search;

import iris.backend_service.configurations.CentralConfiguration;
import iris.backend_service.hd_search.HealthDepartments.HealthDepartment;
import iris.backend_service.jsonrpc.JsonRpcClientDto;
import iris.backend_service.messages.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import com.googlecode.jsonrpc4j.JsonRpcParam;

@Service
@RequiredArgsConstructor
@Slf4j
class HdSearchRpcServieImpl implements HdSearchRpcService {

	private final @NotNull HealthDepartments healthDepartments;
	private final @NotNull CentralConfiguration config;
	private final @NotNull AlertService alerts;

	@Override
	public List<HealthDepartmentDto> searchForHd(@Valid JsonRpcClientDto client,
			@NotEmpty String searchKeyword,
			boolean withDetails,
			boolean alsoNotConnected) {

		var hds = healthDepartments.findDepartmentsContains(searchKeyword);

		var result = hds.parallelStream()
				.map(it -> mapToDto(it, withDetails, alsoNotConnected))
				.flatMap(Optional::stream)
				.toList();

		log.debug("JSON-RPC - Client {} searchs HD for: {} (with details: {}; also not connected HDs: {}) ⇒ found: {}",
				client.getName(), searchKeyword, Boolean.toString(withDetails), Boolean.toString(alsoNotConnected),
				result.size());

		return result;
	}

	@Override
	public List<HealthDepartmentDto> getAllHds(
			@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client,
			boolean withDetails,
			boolean alsoNotConnected) {

		var hds = healthDepartments.getAll();

		var result = hds.parallelStream()
				.map(it -> mapToDto(it, withDetails, alsoNotConnected))
				.flatMap(Optional::stream)
				.toList();

		log.debug("JSON-RPC - Client {} get all HDs (with details: {}; also not connected HDs: {}) ⇒ found: {}",
				client.getName(), Boolean.toString(withDetails), Boolean.toString(alsoNotConnected), result.size());

		return result;
	}

	private Optional<HealthDepartmentDto> mapToDto(HealthDepartment hd, boolean withDetails, boolean alsoNotConnected) {

		var code = hd.getCode();
		var epsName = config.getHdNameFor(code);

		var dto = epsName.map(it -> createHealthDepartmentDto(hd, withDetails, code, it));

		if (alsoNotConnected) {
			dto = dto.or(() -> Optional.of(createHealthDepartmentDto(hd, withDetails, code, null)));
		}

		return dto;
	}

	private HealthDepartmentDto createHealthDepartmentDto(HealthDepartment hd, boolean withDetails, String code,
			String epsName) {

		return new HealthDepartmentDto(hd.getName(),
				code,
				epsName,
				mapToDepartment(hd, withDetails),
				mapToAddress(hd, withDetails),
				mapToContact(hd, withDetails),
				mapToCovidContact(hd, withDetails),
				mapToEntryContact(hd, withDetails));
	}

	private String mapToDepartment(HealthDepartment hd, boolean withDetails) {

		if (withDetails) {
			return hd.getDepartment();
		}
		return null;
	}

	private Address mapToAddress(HealthDepartment hd, boolean withDetails) {

		if (withDetails) {
			var baseAddress = hd.getAddress();
			return new Address(baseAddress.getStreet(), baseAddress.getZipCode(), baseAddress.getPlace());
		}
		return null;
	}

	private ContactData mapToContact(HealthDepartment hd, boolean withDetails) {

		if (withDetails) {
			return new ContactData(hd.getPhone(), hd.getFax(), hd.getEmail());
		}
		return null;
	}

	private ContactData mapToCovidContact(HealthDepartment hd, boolean withDetails) {

		if (withDetails) {
			var covid19ContactData = hd.getCovid19ContactData();
			return new ContactData(covid19ContactData.getHotline(), covid19ContactData.getFax(),
					covid19ContactData.getEmail());
		}
		return null;
	}

	private ContactData mapToEntryContact(HealthDepartment hd, boolean withDetails) {

		if (withDetails) {
			var entryContactData = hd.getEinreiseContactData();
			return new ContactData(entryContactData.getHotline(), entryContactData.getFax(),
					entryContactData.getEmail());
		}
		return null;
	}
}
