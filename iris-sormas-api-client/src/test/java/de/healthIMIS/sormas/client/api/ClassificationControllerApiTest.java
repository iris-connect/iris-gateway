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

import de.healthIMIS.sormas.client.model.DiseaseClassificationCriteriaDto;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for ClassificationControllerApi
 */
@Ignore
public class ClassificationControllerApiTest {

    private final ClassificationControllerApi api = new ClassificationControllerApi();

    /**
     * 
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getAll1Test() {
        Long since = null;
        List<DiseaseClassificationCriteriaDto> response = api.getAll1(since);

        // TODO: test validations
    }
}