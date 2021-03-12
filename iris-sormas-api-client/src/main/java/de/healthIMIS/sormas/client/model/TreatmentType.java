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
 * Gets or Sets TreatmentType
 */
public enum TreatmentType {
  DRUG_INTAKE("DRUG_INTAKE"),
  ORAL_REHYDRATION_SALTS("ORAL_REHYDRATION_SALTS"),
  BLOOD_TRANSFUSION("BLOOD_TRANSFUSION"),
  RENAL_REPLACEMENT_THERAPY("RENAL_REPLACEMENT_THERAPY"),
  IV_FLUID_THERAPY("IV_FLUID_THERAPY"),
  OXYGEN_THERAPY("OXYGEN_THERAPY"),
  INVASIVE_MECHANICAL_VENTILATION("INVASIVE_MECHANICAL_VENTILATION"),
  VASOPRESSORS_INOTROPES("VASOPRESSORS_INOTROPES"),
  OTHER("OTHER");

  private String value;

  TreatmentType(String value) {
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
  public static TreatmentType fromValue(String text) {
    for (TreatmentType b : TreatmentType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}