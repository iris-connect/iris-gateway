package de.healthIMIS.iris.client.users.web;

import static de.healthIMIS.iris.client.users.web.UserMappers.map;

import de.healthIMIS.iris.client.users.UserDetailsServiceImpl;
import de.healthIMIS.iris.client.users.web.dto.UserDTO;
import de.healthIMIS.iris.client.users.web.dto.UserListDTO;
import de.healthIMIS.iris.client.users.web.dto.UserUpsertDTO;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@AllArgsConstructor
@RequestMapping(UserController.USER_API_PATH)
public class UserController {

	public static final String USER_API_PATH = "/users";

	private final UserDetailsServiceImpl userService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public UserListDTO getAllUsers() {
		return new UserListDTO().users(
				this.userService
						.loadAll()
						.stream()
						.map(UserMappers::map)
						.collect(Collectors.toList()));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO createUser(@RequestBody UserUpsertDTO userUpsert) {
		// TODO validation
		return map(userService.upsert(userUpsert));
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO updateUser(@PathVariable String id, @RequestBody UserUpsertDTO userUpsertDTO) {
		// TODO validation
		// TODO keep id? username in userUpserDTO seems sufficient
		System.out.println("id = " + id);
		return map(userService.upsert(userUpsertDTO));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(@PathVariable String id) {
		this.userService.deleteUserById(id);
	}
}
