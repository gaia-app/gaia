package io.gaia_app.config.security.oauth2

import io.gaia_app.config.security.SuccessHandler
import io.gaia_app.registries.RegistryOAuth2Provider
import io.gaia_app.organizations.OAuth2User
import io.gaia_app.organizations.User
import io.gaia_app.organizations.repository.UserRepository
import org.springframework.boot.autoconfigure.security.oauth2.client.ClientsConfiguredCondition
import org.springframework.context.annotation.Conditional
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Conditional(ClientsConfiguredCondition::class)
class OAuth2SuccessHandler(
        userRepository: UserRepository,
        val registryOAuth2Providers: List<RegistryOAuth2Provider>,
        val oAuth2AuthorizedClientService: OAuth2AuthorizedClientService) : SuccessHandler(userRepository) {

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        // get user if exist, otherwise create a new one
        val user = userRepository
            .findById(authentication.name)
            .orElse(User(authentication.name, null))

        // get oauth2 data
        user.oAuth2User = getOAuth2User(authentication as OAuth2AuthenticationToken)
        userRepository.save(user)

        response.sendRedirect("/")
    }

    private fun getOAuth2User(authentication: OAuth2AuthenticationToken): OAuth2User? {
        val client = oAuth2AuthorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
            authentication.authorizedClientRegistrationId,
            authentication.name)

        return registryOAuth2Providers
            .filter { it.isAssignableFor(client.clientRegistration.registrationId) }
            .map { it.getOAuth2User(authentication.principal as DefaultOAuth2User, client) }
            .firstOrNull()
    }

}
