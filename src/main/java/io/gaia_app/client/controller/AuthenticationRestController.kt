package io.gaia_app.client.controller

import io.gaia_app.organizations.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthenticationRestController(
    @Autowired(required = false) var oAuth2ClientProperties: OAuth2ClientProperties? = null) {

    @GetMapping("/user")
    fun user(@ModelAttribute user: User) = user

    @GetMapping("/authorities")
    fun authorities(authentication: Authentication) = authentication.authorities.map { it.authority }

    @GetMapping("/providers")
    fun providers(): Set<String> =
        if (oAuth2ClientProperties != null) {
            oAuth2ClientProperties!!.registration.keys
        } else setOf()

}

