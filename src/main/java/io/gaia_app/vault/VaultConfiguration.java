package io.gaia_app.vault;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.LifecycleAwareSessionManager;
import org.springframework.vault.authentication.SessionManager;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URI;

@Configuration
@ConditionalOnProperty(name="gaia.vault.enabled", havingValue = "true")
public class VaultConfiguration {

    @Value("${gaia.vault.uri:https://localhost:8200}")
    private String vaultUri;

    @Bean
    VaultEndpoint vaultEndpoint(){
        return VaultEndpoint.from(URI.create(vaultUri));
    }

    @Bean
    @ConditionalOnProperty("gaia.vault.authentication.token")
    ClientAuthentication vaultAuthentication(@Value("${gaia.vault.authentication.token}") String token){
        return new TokenAuthentication(token);
    }

    @Bean
    TaskScheduler vaultSessionTaskScheduler(){
        return new ThreadPoolTaskScheduler();
    }

    @Bean
    SessionManager vaultSessionManager(ClientAuthentication clientAuthentication){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(this.vaultUri));
        return new LifecycleAwareSessionManager(clientAuthentication, vaultSessionTaskScheduler(), restTemplate);
    }

    @Bean
    VaultTemplate vaultTemplate(VaultEndpoint vaultEndpoint, SessionManager vaultSessionManager){
        return new VaultTemplate(vaultEndpoint, new SimpleClientHttpRequestFactory(), vaultSessionManager);
    }

}
