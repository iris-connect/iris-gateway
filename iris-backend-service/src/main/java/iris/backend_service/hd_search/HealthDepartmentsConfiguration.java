package iris.backend_service.hd_search;

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
 * Uses the <a href="https://survnet.rki.de/Content/Service/DownloadHandler.ashx?Id=82">data from RKI</a> to offer the
 * search for health departments by zip code or location in one service.
 *
 * @author Jens Kutzsche
 * @author Oliver Drotbohm
 */
@Configuration
class HealthDepartmentsConfiguration {

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
