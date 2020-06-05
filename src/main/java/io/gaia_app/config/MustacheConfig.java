package io.gaia_app.config;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration for mustache template
 */
@Configuration
public class MustacheConfig {

    @Bean
    Mustache terraformMustache() {
        var factory = new DefaultMustacheFactory();
        return factory.compile("mustache/terraform.mustache");
    }

}
