package io.codeka.gaia.config.security;

import org.springframework.beans.factory.annotation.Autowired;
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
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${gaia.admin-password:admin123}")
    private String adminPassword;

    private SuccessHandler successHandler;

    @Autowired
    public SecurityConfig(SuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

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
                    .antMatchers("/webjars/**").permitAll()
                    .antMatchers("/css/**", "/js/**", "/favicon.ico", "/images/**").permitAll()
                    .antMatchers("/**").authenticated()
                .and()
                .formLogin().loginPage("/login").successHandler(successHandler).permitAll()
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
    }
}
