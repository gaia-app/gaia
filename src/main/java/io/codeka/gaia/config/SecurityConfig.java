package io.codeka.gaia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${gaia.ldap.userDnPatterns:}")
    private String userDnPatterns;

    @Value("${gaia.ldap.url:}")
    private String url;

    @Value("${gaia.ldap.enabled:false}")
    private boolean ldapEnabled;

    @Value("${gaia.admin-password:admin123}")
    private String adminPassword;

    @Bean
    PasswordEncoder bcrypt(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/api/**").permitAll()
                    .antMatchers("/**").authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // configure default admin user
        auth
            .inMemoryAuthentication()
                .withUser("admin").password(bcrypt().encode(adminPassword)).authorities("ROLE_ADMIN")
                .and()
                .withUser("user").password(bcrypt().encode("user123")).authorities("ROLE_USER");

        // configure ldap auth if needed
        if(ldapEnabled){
            auth
                    .ldapAuthentication()
                    .userDnPatterns(userDnPatterns)
                    .contextSource()
                    .url(url);
        }
    }

}
