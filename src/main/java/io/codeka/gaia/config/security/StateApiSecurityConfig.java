package io.codeka.gaia.config.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Configuration
@Order(69)
public class StateApiSecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder bCrypt;

    private static final Log logger = LogFactory.getLog(StateApiSecurityConfig.class);

    @Autowired
    public StateApiSecurityConfig(PasswordEncoder bCrypt) {
        this.bCrypt = bCrypt;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .antMatcher("/api/state/**")
                .authorizeRequests()
                    .anyRequest().hasAnyRole("STATE", "USER")
                .and()
                .httpBasic();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.info(String.format("%n%nUsing generated security password for state API: %s%n", properties().getPassword()));

        // configure default backend user
        auth
                .inMemoryAuthentication()
                .withUser(properties().getUsername()).password(bCrypt.encode(properties().getPassword())).authorities("ROLE_STATE");
    }

    @Bean
    @ConfigurationProperties(prefix = "gaia.state.api")
    public StateApiSecurityProperties properties(){
        return new StateApiSecurityProperties("gaia-backend", UUID.randomUUID().toString());
    }

    public static class StateApiSecurityProperties {

        private String password;

        private String username;

        public StateApiSecurityProperties(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

    }

}
