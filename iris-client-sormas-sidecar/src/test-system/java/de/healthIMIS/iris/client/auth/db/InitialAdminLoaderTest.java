package de.healthIMIS.iris.client.auth.db;

import de.healthIMIS.iris.client.auth.db.model.UserAccountsRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@SpringBootTest
class InitialAdminLoaderTest {

    @Autowired
    private UserAccountsRepository repo;

    @Test
    public void shouldCreateAnAdminUserIfItDoesNotExist() {
        assertTrue(repo.findByUserName("admin").isPresent());
    }
}