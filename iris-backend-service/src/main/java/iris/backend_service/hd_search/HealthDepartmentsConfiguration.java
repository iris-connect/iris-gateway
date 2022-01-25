package iris.backend_service.hd_search;

import static org.apache.commons.lang3.ArrayUtils.contains;
import static org.apache.commons.lang3.StringUtils.*;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
@RequiredArgsConstructor
class HealthDepartmentsConfiguration {

	private static final ZoneId ZONE_BERLIN = ZoneId.of("Europe/Berlin");

	private final Environment env;

	@Value("classpath:masterdata/TransmittingSiteSearchText_dev.xml")
	File devFile;

	@Value("classpath:masterdata/TransmittingSiteSearchText_test.xml")
	File testFile;

	@Bean
	HealthDepartments healthDepartments() {

		var config = new DefaultXMLFactoriesConfig().setNamespacePhilosophy(NamespacePhilosophy.AGNOSTIC);
		var projector = new XBProjector(config);

		projector.config()
				.setTypeConverter(new DefaultTypeConverter(Locale.getDefault(), TimeZone.getTimeZone(ZONE_BERLIN)));

		try {

			var hds = new ArrayList<HealthDepartments>();
			hds.add(projector.io().fromURLAnnotation(HealthDepartments.class));

			if (Arrays.stream(env.getActiveProfiles())
					.anyMatch(it -> equalsAny(it, "stage_dev", "local", "dev", "dev_env", "inttest"))) {

				hds.add(projector.io().file(devFile).read(HealthDepartments.class));
			}

			if (contains(env.getActiveProfiles(), "stage_test")) {
				hds.add(projector.io().file(testFile).read(HealthDepartments.class));
			}

			return new MultiHealthDepartments(hds);
		} catch (IOException e) {
			throw new IllegalStateException("Can't initialize service HealthDepartmentOverview!", e);
		}
	}
}
