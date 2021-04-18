package de.healthIMIS.iris.client.auth.web;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UsersListDTO {
    private List<UserDTO> users;
}
