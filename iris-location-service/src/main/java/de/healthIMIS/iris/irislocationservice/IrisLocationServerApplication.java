package de.healthIMIS.iris.irislocationservice;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IrisLocationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IrisLocationServerApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		var mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return mapper;
	}
}
