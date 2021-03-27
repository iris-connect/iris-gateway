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
package de.healthIMIS.iris.public_server.web;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
		date = "2021-02-18T08:11:24.698Z[GMT]")
@Configuration
public class SwaggerDocumentationConfig {

	@Bean
	public Docket customImplementation() {
		return new Docket(DocumentationType.OAS_30).select().apis(RequestHandlerSelectors.basePackage("io.swagger.api"))
				.build().directModelSubstitute(LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(OffsetDateTime.class, java.util.Date.class).apiInfo(apiInfo());
	}

	ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("IRIS Public API").description(
				"The public API of IRIS (Integration of Remote systems Into SORMAS). Through this API, the many contact tracking apps and the like can submit your data to health departments via IRIS")
				.license("GNU AGPL v3").licenseUrl("https://www.gnu.org/licenses/agpl-3.0.de.html").termsOfServiceUrl("")
				.version("1.0.0").contact(new Contact("", "", "jens.kutzsche@gebea.de")).build();
	}

	@Bean
	public OpenAPI openApi() {
		return new OpenAPI().info(new Info().title("IRIS Public API").description(
				"The public API of IRIS (Integration of Remote systems Into SORMAS). Through this API, the many contact tracking apps and the like can submit your data to health departments via IRIS")
				.termsOfService("").version("1.0.0")
				.license(new License().name("GNU AGPL v3").url("https://www.gnu.org/licenses/agpl-3.0.de.html"))
				.contact(new io.swagger.v3.oas.models.info.Contact().email("jens.kutzsche@gebea.de")));
	}

}
