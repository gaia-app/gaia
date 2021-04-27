package io.gaia_app.config.security.ldap;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = LdapAutoConfiguration.class)
@ConditionalOnProperty(prefix = "gaia", name = "ldap.enabled", havingValue = "false", matchIfMissing = true)
public class CustomLdapAutoConfiguration {
    // this blocks the load of ldap auto configuration if the ldap is not enabled
}
