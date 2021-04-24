package de.healthIMIS.iris.client.auth.db;

import de.healthIMIS.iris.client.auth.db.model.UserAccount;
import de.healthIMIS.iris.client.auth.db.model.UserAccountsRepository;
import de.healthIMIS.iris.client.auth.db.model.UserRole;
import de.healthIMIS.iris.client.users.web.dto.UserUpsertDTO;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserAccountsRepository userAccountsRepository;

	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) {
		UserAccount userAccount = userAccountsRepository.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));

		// By convention we expect that there exists only one authority and it represents the role
		var role = userAccount.getRole().name();
		var authorities = List.of(new SimpleGrantedAuthority(role));

		return new User(userAccount.getUserName(), userAccount.getPassword(), authorities);
	}

	public Optional<UserAccount> findByUsername(String username) {
		return userAccountsRepository.findByUserName(username);
	}

	public List<UserAccount> loadAll() {
		return StreamSupport
				.stream(userAccountsRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}

	public UserAccount upsert(UserUpsertDTO userUpsert) {
		var upsertable = new AtomicReference<UserAccount>();
		userAccountsRepository
				.findByUserName(userUpsert.getUserName())
				.ifPresentOrElse(upsertable::set, () -> upsertable.set(map(userUpsert)));

		return userAccountsRepository.save(upsertable.get());
	}

	private UserAccount map(UserUpsertDTO userDTO) {
		var user = new UserAccount();
		user.setRole(UserRole.valueOf(userDTO.getRole().name()));
		user.setUserName(userDTO.getUserName());
		user.setLastName(userDTO.getLastName());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setFirstName(userDTO.getFirstName());
		return user;
	}
}
