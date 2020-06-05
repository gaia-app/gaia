package io.gaia_app.config.security.actuator;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Actuator security configuration.
 * This configuration has an order of 50, to be executed before the general security configuration, which locks all endpoints.
 */
@Configuration
@Order(50)
public class ActuatorSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requestMatcher(EndpointRequest.to("health", "info"))
                .authorizeRequests()
                .anyRequest()
                .permitAll();
    }
}
