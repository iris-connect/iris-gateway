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

import de.healthIMIS.sormas.client.model.CompatibilityCheckResponse;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for InfoControllerApi
 */
@Ignore
public class InfoControllerApiTest {

	private final InfoControllerApi api = new InfoControllerApi();

	/**
	 * @throws ApiException if the Api call fails
	 */
	@Test
	public void getAppUrlTest() {
		String appVersion = null;
		String response = api.getAppUrl(appVersion);

		// TODO: test validations
	}

	/**
	 * @throws ApiException if the Api call fails
	 */
	@Test
	public void getLocaleTest() {
		String response = api.getLocale();

		// TODO: test validations
	}

	/**
	 * @throws ApiException if the Api call fails
	 */
	@Test
	public void getVersion1Test() {
		String response = api.getVersion1();

		// TODO: test validations
	}

	/**
	 * @throws ApiException if the Api call fails
	 */
	@Test
	public void isCompatibleToApiTest() {
		String appVersion = null;
		CompatibilityCheckResponse response = api.isCompatibleToApi(appVersion);

		// TODO: test validations
	}
}
