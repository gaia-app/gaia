package io.codeka.gaia.config.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.ClientsConfiguredCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
@Order(70)
@Conditional(ClientsConfiguredCondition.class)
public class OAuth2ClientSecurityConfig extends WebSecurityConfigurerAdapter {

    private OAuth2SuccessHandler oAuth2SuccessHandler;

    @Autowired
    public OAuth2ClientSecurityConfig(OAuth2SuccessHandler oAuth2SuccessHandler) {
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        var requestMatcher = new OrRequestMatcher(
                // connection to oauth2 client
                new AntPathRequestMatcher("/oauth2/authorization/*"),
                // oauth2 client callback
                new AntPathRequestMatcher("/login/oauth2/code/*")
        );
        http
                .requestMatcher(requestMatcher)
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .successHandler(oAuth2SuccessHandler).permitAll();
    }
}
