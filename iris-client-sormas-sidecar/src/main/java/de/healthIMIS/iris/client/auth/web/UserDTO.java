package de.healthIMIS.iris.client.auth.web;

import lombok.Data;

@Data
public class UserDTO {
    private String userName;
    private String firstName = "";
    private String lastName = "";
}
