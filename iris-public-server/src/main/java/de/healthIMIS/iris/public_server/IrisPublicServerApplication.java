/*******************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package de.healthIMIS.iris.public_server;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.oas.annotations.EnableOpenApi;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing(dateTimeProviderRef = "irisDateTimeProvider")
@EnableOpenApi
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class IrisPublicServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IrisPublicServerApplication.class, args);
	}

	@Autowired
	Environment env;

	@Bean
	FlywayMigrationStrategy getFlywayMigrationStrategy() {

		return flyway -> {

			var profiles_clean = List.of("psql_compose_db");
			if (Arrays.stream(env.getActiveProfiles()).anyMatch(profiles_clean::contains)) {
				flyway.clean();
			}

			if (log.isDebugEnabled()) {

				var results = flyway.validateWithResult();

				results.invalidMigrations.forEach(it -> {

					var errorDetails = it.errorDetails;

					log.debug(
						"ValidateOutput: " + it.description + errorDetails != null
							? " | ErrorCode: " + errorDetails.errorCode + " | ErrorMessage: " + errorDetails.errorMessage
							: "");
				});
			}

			flyway.migrate();
		};
	}
}
