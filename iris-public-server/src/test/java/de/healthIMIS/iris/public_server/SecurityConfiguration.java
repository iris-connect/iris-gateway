package de.healthIMIS.iris.public_server;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@TestConfiguration
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void init(WebSecurity builder) throws Exception {
		builder.ignoring().requestMatchers(new AntPathRequestMatcher("/**"));
	}
}
