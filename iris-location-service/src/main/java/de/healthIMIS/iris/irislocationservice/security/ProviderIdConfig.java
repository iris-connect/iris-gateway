package de.healthIMIS.iris.irislocationservice.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Configuration
@EnableWebSecurity
@Order(1)
@Slf4j
public class ProviderIdConfig extends WebSecurityConfigurerAdapter {

    @Value("${iris.search-index.provider-id-header}")
    private String requestHeaderName;

    @Autowired
    private AllowedProviders allowedProviders;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ProviderIdAuthFilter filter = new ProviderIdAuthFilter(requestHeaderName);
        filter.setAuthenticationManager(new AuthenticationManager() {

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String principal = (String) authentication.getPrincipal();
                if (!allowedProviders.providers.containsKey(principal))
                {
                    throw new BadCredentialsException("ProviderId was not found in header.");
                }
                log.info("Request from "+allowedProviders.providers.get(principal));
                authentication.setAuthenticated(true);
                return authentication;
            }
        });
        httpSecurity.
                antMatcher("/search-index/**").
                csrf().disable().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().addFilter(filter).authorizeRequests().anyRequest().authenticated();
    }

}
