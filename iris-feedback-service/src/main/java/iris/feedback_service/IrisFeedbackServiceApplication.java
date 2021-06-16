package iris.feedback_service;

import iris.feedback_service.IrisFeedbackServiceApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/*
 * @author Ostfalia Gruppe 12
 */
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableAutoConfiguration(exclude = {
	DataSourceAutoConfiguration.class,
	HibernateJpaAutoConfiguration.class })
public class IrisFeedbackServiceApplication {

	public static void main(String[] args) {

		var application = new SpringApplication(IrisFeedbackServiceApplication.class);
		application.run(args);
	}
}
