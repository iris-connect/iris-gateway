package de.healthIMIS.iris.client.users.web.dto;

import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets UserRole
 */
public enum UserRoleDTO {
  
  ADMIN("ADMIN"),
  
  USER("USER");

  private String value;

  UserRoleDTO(String value) {
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
  public static UserRoleDTO fromValue(String value) {
    for (UserRoleDTO b : UserRoleDTO.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

