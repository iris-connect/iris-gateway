package de.healthIMIS.iris.client.auth.web;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.healthIMIS.iris.client.auth.db.model.UserAccountsRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class UsersController {
    private UserAccountsRepository repo;
  @GetMapping("/users")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<UsersListDTO> getUsersList() {
      var users = new ArrayList<UserDTO>();
      repo.findAll().forEach(u->{
          var dto = new UserDTO();
          dto.setUserName(u.getUserName());
          dto.setFirstName(u.getFirstName() == null ? "" : u.getFirstName());
          dto.setLastName(u.getLastName() == null ? "" : u.getLastName());
          users.add(dto);
      });
      var uldto = new UsersListDTO(users);
      
    // TODO: Authenticate API Access
    return new ResponseEntity<UsersListDTO>(uldto, HttpStatus.OK);
   }

}
