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

import de.healthIMIS.sormas.client.model.CommunityDto;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for CommunityControllerApi
 */
@Ignore
public class CommunityControllerApiTest {

    private final CommunityControllerApi api = new CommunityControllerApi();

    /**
     * 
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getAll2Test() {
        Long since = null;
        List<CommunityDto> response = api.getAll2(since);

        // TODO: test validations
    }
    /**
     * 
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getAllUuids6Test() {
        List<String> response = api.getAllUuids6();

        // TODO: test validations
    }
    /**
     * 
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getByUuids8Test() {
        List<String> body = null;
        List<CommunityDto> response = api.getByUuids8(body);

        // TODO: test validations
    }
}
