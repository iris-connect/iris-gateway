package de.healthIMIS.iris.irislocationservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.ResourceBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@SpringBootApplication
@ConfigurationPropertiesScan
public class IrisLocationServerApplication {

	public static void main(String[] args) throws Exception {

		var properties = PropertiesLoaderUtils.loadAllProperties("git.properties");
		var banner = new ResourceBanner(new ClassPathResource("iris-banner.txt")) {
			@Override
			protected String getApplicationVersion(Class<?> sourceClass) {
				return properties.getProperty("git.build.version", "-") + " ("
						+ properties.getProperty("git.commit.id.abbrev", "-")
						+ ")";
			}
		};

		var application = new SpringApplication(IrisLocationServerApplication.class);
		application.setBanner(banner);
		application.run(args);
	}

	@Bean
	public ModelMapper modelMapper() {
		
		var mapper = new ModelMapper();
		// mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		mapper.getConfiguration().setAmbiguityIgnored(true);
		// mapper.typeMap(Location.class, LocationInformation.class).addMappings(m -> m.skip(LocationInformation::setId));

		return mapper;
	}
}
