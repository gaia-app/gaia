package io.codeka.gaia.config.security.oauth2;

import io.codeka.gaia.config.security.SuccessHandler;
import io.codeka.gaia.registries.RegistryOAuth2Provider;
import io.codeka.gaia.teams.OAuth2User;
import io.codeka.gaia.teams.User;
import io.codeka.gaia.teams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.ClientsConfiguredCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@Conditional(ClientsConfiguredCondition.class)
public class OAuth2SuccessHandler extends SuccessHandler {

    private List<RegistryOAuth2Provider> registryOAuth2Providers;
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Autowired
    public OAuth2SuccessHandler(UserRepository userRepository, List<RegistryOAuth2Provider> registryOAuth2Providers,
                                OAuth2AuthorizedClientService oAuth2AuthorizedClientService) {
        super(userRepository);
        this.registryOAuth2Providers = registryOAuth2Providers;
        this.oAuth2AuthorizedClientService = oAuth2AuthorizedClientService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // get user if exist, otherwise create a new one
        var user = userRepository.findById(authentication.getName())
                .orElse(new User(authentication.getName(), null));
        // get oauth2 data
        user.setOAuth2User(getOAuth2User((OAuth2AuthenticationToken) authentication));
        userRepository.save(user);
        redirect(request, response);
    }

    private OAuth2User getOAuth2User(OAuth2AuthenticationToken authentication) {
        var client = oAuth2AuthorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());
        var user = (DefaultOAuth2User) authentication.getPrincipal();
        return registryOAuth2Providers.stream()
                .filter(p -> p.isAssignableFor(client.getClientRegistration().getRegistrationId()))
                .map(p -> p.getOAuth2User(user, client))
                .findFirst().orElse(null);
    }
}