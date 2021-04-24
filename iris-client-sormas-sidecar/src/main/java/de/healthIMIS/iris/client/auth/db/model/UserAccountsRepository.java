package de.healthIMIS.iris.client.auth.db.model;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface UserAccountsRepository extends CrudRepository<UserAccount, UUID> {
	Optional<UserAccount> findByUserName(String userName);
}
