package io.gaia_app.config.security.ldap;

import io.gaia_app.test.SharedMongoContainerTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Testcontainers
class LdapSecurityConfigIT {

    @Nested
    @SpringBootTest(properties = "gaia.ldap.enabled=false")
    class LdapSecurityConfigNotLoadedTest extends SharedMongoContainerTest {
        @Test
        void ldapSecurityConfig_shouldNotBeInstantiated(
                @Autowired(required = false) LdapSecurityConfig ldapSecurityConfig) {
            assertNull(ldapSecurityConfig);
        }
    }

    @Nested
    @SpringBootTest(properties = {
            "gaia.ldap.enabled=true",
            "gaia.ldap.userDnPatterns=test_dn",
            "gaia.ldap.url=ldap://test_url",
    })
    class LdapSecurityConfigLoadedTest extends SharedMongoContainerTest {
        @Test
        void ldapSecurityConfig_shouldBeInstantiated(
                @Autowired(required = false) LdapSecurityConfig ldapSecurityConfig) {
            assertNotNull(ldapSecurityConfig);
        }
    }
}
