package de.healthIMIS.iris.irislocationservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class IrisLocationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IrisLocationServerApplication.class, args);
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
