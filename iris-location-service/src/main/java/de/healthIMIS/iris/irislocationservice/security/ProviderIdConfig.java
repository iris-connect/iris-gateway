package de.healthIMIS.iris.irislocationservice.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@Slf4j
public class ProviderIdConfig extends WebSecurityConfigurerAdapter {

    private static final String requestHeaderName = "x-provider-id";

    private final @NotNull AllowedProviders allowedProviders;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ProviderIdAuthFilter filter = new ProviderIdAuthFilter(requestHeaderName);
        filter.setAuthenticationManager(new AuthenticationManager() {

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String principal = (String) authentication.getPrincipal();
                AllowedProviders.Provider provider = allowedProviders.findByProviderId(principal);
                if (provider == null)
                {
                    throw new BadCredentialsException("ProviderId was not found in header.");
                }
                log.info("Request from "+provider.name);
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
