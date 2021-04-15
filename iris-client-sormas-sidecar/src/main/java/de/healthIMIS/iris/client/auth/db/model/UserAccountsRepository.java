package de.healthIMIS.iris.client.auth.db.model;

import de.healthIMIS.iris.client.data_request.DataRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAccountsRepository extends CrudRepository<UserAccount, Long> {

    Optional<UserAccount> findByUserName(String userName);

}
