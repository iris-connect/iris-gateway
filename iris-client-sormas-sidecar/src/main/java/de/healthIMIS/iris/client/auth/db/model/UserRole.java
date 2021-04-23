package de.healthIMIS.iris.client.auth.db.model;

/**
 * Gets or Sets UserRole
 */
public enum UserRole {

  ADMIN("ADMIN"),

  USER("USER");

  private String value;

  UserRole(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public static UserRole fromValue(String text) {
    for (UserRole b : UserRole.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

