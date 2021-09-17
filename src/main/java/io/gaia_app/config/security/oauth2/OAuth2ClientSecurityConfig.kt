package io.gaia_app.config.security.oauth2

import org.springframework.boot.autoconfigure.security.oauth2.client.ClientsConfiguredCondition
import org.springframework.context.annotation.Conditional
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher

@Configuration
@Order(70)
@Conditional(ClientsConfiguredCondition::class)
class OAuth2ClientSecurityConfig(
    val oAuth2SuccessHandler: OAuth2SuccessHandler) : WebSecurityConfigurerAdapter() {

    private val oauth2Endpoint = "/auth/oauth2"
    private val oauth2Callback = "$oauth2Endpoint/*/callback"

    override fun configure(http: HttpSecurity) {
        val requestMatcher = OrRequestMatcher(
            AntPathRequestMatcher("$oauth2Endpoint/*"),
            AntPathRequestMatcher(oauth2Callback)
        )
        // @formatter:off
        http
            .requestMatcher(requestMatcher)
            .authorizeRequests()
            .anyRequest()
            .permitAll()
        .and()
            .oauth2Login()
            .loginProcessingUrl(oauth2Callback)
            .authorizationEndpoint { it.baseUri(oauth2Endpoint) }
            .successHandler(oAuth2SuccessHandler)
            .permitAll()
        // @formatter:on
    }

}
