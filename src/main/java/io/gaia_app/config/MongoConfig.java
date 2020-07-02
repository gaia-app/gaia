package io.gaia_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "io.gaia_app")
@EnableMongoAuditing
public class MongoConfig {

    @Autowired
    void setMapKeyDotReplacement(MappingMongoConverter mappingMongoConverter) {
        mappingMongoConverter.setMapKeyDotReplacement("#");
    }

}

