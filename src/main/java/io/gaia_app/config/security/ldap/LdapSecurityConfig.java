package io.gaia_app.config.security.ldap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@ConditionalOnProperty(prefix = "gaia", name = "ldap.enabled", havingValue = "true")
@Order(60)
public class LdapSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${gaia.ldap.userDnPatterns}")
    private String userDnPatterns;

    @Value("${gaia.ldap.url}")
    private String url;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns(userDnPatterns)
                .contextSource()
                .url(url);
    }
}
