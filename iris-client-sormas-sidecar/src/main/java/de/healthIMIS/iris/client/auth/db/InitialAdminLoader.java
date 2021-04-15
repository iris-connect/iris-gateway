package de.healthIMIS.iris.client.auth.db;

import de.healthIMIS.iris.client.auth.db.model.UserAccount;
import de.healthIMIS.iris.client.auth.db.model.UserAccountsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
@Slf4j
public class InitialAdminLoader {

    private DbAuthConfiguration conf;

    private UserAccountsRepository repo;

    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void loadAdmin() {
        if (conf.getAdminUserName() != null && conf.getAdminUserPassword() != null) {
            log.info("Create admin user [{}]", conf.getAdminUserName());
            var userAccount = new UserAccount();
            userAccount.setUserName(conf.getAdminUserName());
            userAccount.setPassword(passwordEncoder.encode(conf.getAdminUserPassword()));
            repo.save(userAccount);
        } else {
            log.info("No admin user configured. Skip creating admin user.");
        }
    }

}
