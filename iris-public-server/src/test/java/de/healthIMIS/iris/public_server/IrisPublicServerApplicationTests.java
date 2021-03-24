package de.healthIMIS.iris.public_server;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({
	"dev",
	"inttest" })
@Disabled
class IrisPublicServerApplicationTests {

	@Test
	void contextLoads() {
	}

}
