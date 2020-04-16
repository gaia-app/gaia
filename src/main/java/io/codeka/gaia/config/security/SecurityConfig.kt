package io.codeka.gaia.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.csrf.CookieCsrfTokenRepository

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfig(
    val successHandler: SuccessHandler,
    val failureHandler: FailureHandler,
    val logoutSuccessHandler: LogoutSuccessHandler,
    val accessDeniedHandler: AccessDeniedHandler,
    val authenticationEntryPoint: AuthenticationEntryPoint) : WebSecurityConfigurerAdapter() {

    @Value("\${gaia.admin-password:admin123}")
    private val adminPassword: String? = null

    @Value("\${gaia.remember-me-secret:gaia-secret}")
    private val rememberMeSecret: String? = null

    @Bean
    fun bcrypt(): PasswordEncoder = BCryptPasswordEncoder()

    override fun configure(web: WebSecurity) {
        web.ignoring()
            .antMatchers("/assets/**")
            .antMatchers("/favicon.ico")
    }

    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler)
        .and()
            .formLogin()
            .loginProcessingUrl("/auth/classic")
            .successHandler(successHandler)
            .failureHandler(failureHandler)
            .permitAll()
        .and()
            .rememberMe()
            .key(rememberMeSecret)
        .and()
            .logout()
            .logoutUrl("/auth/logout")
            .deleteCookies("JSESSIONID")
            .logoutSuccessHandler(logoutSuccessHandler)
            .permitAll()
        .and()
            .headers()
            .frameOptions()
            .deny()
        .and()
            .authorizeRequests()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/auth/user").authenticated()
            .antMatchers("/auth/authorities").authenticated()
            .antMatchers("/auth/providers").permitAll()
            .antMatchers("/build-info").permitAll()
        // @formatter:on
    }

    public override fun configure(auth: AuthenticationManagerBuilder) {
        // @formatter:off
        auth
            .inMemoryAuthentication()
            // configure default admin user
            .withUser("admin").password(bcrypt().encode(adminPassword)).authorities("ROLE_ADMIN", "ROLE_USER")
        .and()
            .withUser("user").password(bcrypt().encode("user123")).authorities("ROLE_USER")
        // @formatter:on
    }

}
