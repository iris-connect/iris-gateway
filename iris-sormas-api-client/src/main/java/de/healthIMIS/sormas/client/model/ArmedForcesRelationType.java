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

package de.healthIMIS.sormas.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets ArmedForcesRelationType
 */
public enum ArmedForcesRelationType {
  UNKNOWN("UNKNOWN"),
  NO_RELATION("NO_RELATION"),
  CIVIL("CIVIL"),
  SOLDIER_OR_RELATIVE("SOLDIER_OR_RELATIVE");

  private String value;

  ArmedForcesRelationType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ArmedForcesRelationType fromValue(String text) {
    for (ArmedForcesRelationType b : ArmedForcesRelationType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}