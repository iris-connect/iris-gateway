/*
 * SORMAS REST API
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.55.0-SNAPSHOT
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package de.healthIMIS.sormas.client.api;

import de.healthIMIS.sormas.client.model.CampaignDto;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for CampaignControllerApi
 */
@Ignore
public class CampaignControllerApiTest {

	private final CampaignControllerApi api = new CampaignControllerApi();

	/**
	 * @throws ApiException if the Api call fails
	 */
	@Test
	public void getAllCampaignFormData1Test() {
		Long since = null;
		List<CampaignDto> response = api.getAllCampaignFormData1(since);

		// TODO: test validations
	}

	/**
	 * @throws ApiException if the Api call fails
	 */
	@Test
	public void getAllUuids4Test() {
		List<String> response = api.getAllUuids4();

		// TODO: test validations
	}

	/**
	 * @throws ApiException if the Api call fails
	 */
	@Test
	public void getByUuids5Test() {
		List<String> body = null;
		List<CampaignDto> response = api.getByUuids5(body);

		// TODO: test validations
	}
}
