package de.healthIMIS.iris.public_server.rki;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xmlbeam.XBProjector;
import org.xmlbeam.config.DefaultXMLFactoriesConfig;
import org.xmlbeam.config.DefaultXMLFactoriesConfig.NamespacePhilosophy;
import org.xmlbeam.types.DefaultTypeConverter;

/**
 * Uses the <a href="https://www.rki.de/DE/Content/Infekt/IfSG/Software/Aktueller_Datenbestand.html">data from RKI</a>
 * to offer the search for health departments by zip code or location in one service.
 *
 * @author Jens Kutzsche
 */
@Configuration
public class HealthDepartmentsConfiguration {

	private static final ZoneId ZONE_BERLIN = ZoneId.of("Europe/Berlin");

	@Bean
	HealthDepartments healthDepartments() {

		var config = new DefaultXMLFactoriesConfig().setNamespacePhilosophy(NamespacePhilosophy.AGNOSTIC);
		var projector = new XBProjector(config);

		projector.config()
				.setTypeConverter(new DefaultTypeConverter(Locale.getDefault(), TimeZone.getTimeZone(ZONE_BERLIN)));

		try {
			return projector.io().fromURLAnnotation(HealthDepartments.class);
		} catch (IOException e) {
			throw new IllegalStateException("Can't initialize service HealthDepartmentOverview!", e);
		}
	}
}
