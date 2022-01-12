package iris.backend_service.hd_search;

import iris.backend_service.jsonrpc.JsonRpcClientDto;

import java.util.List;

import javax.validation.Valid;

import com.googlecode.jsonrpc4j.JsonRpcParam;

public interface HdSearchRpcService {

	List<HealthDepartmentDto> searchForHd(@Valid @JsonRpcParam(value = "_client") JsonRpcClientDto client,
			@JsonRpcParam(value = "searchKeyword") String searchKeyword);

	record HealthDepartmentDto(String name,
			String rkiCode,
			String epsName,
			String department,
			Address address,
			ContactData contactData,
			ContactData covid19ContactData,
			ContactData entryContactData) {}

	record Address(String street, String zipCode, String city) {}

	record ContactData(String phone, String fax, String eMail) {}
}
