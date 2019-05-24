package io.codeka.gaia.config;

import io.codeka.gaia.bo.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@Configuration
public class RepositoryConfig {

    @Autowired
    public void configureRest(RepositoryRestConfiguration repositoryRestConfiguration){
        repositoryRestConfiguration.exposeIdsFor(Stack.class);

    }
}
