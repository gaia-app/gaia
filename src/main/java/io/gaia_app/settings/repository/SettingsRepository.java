package io.gaia_app.settings.repository;

import io.gaia_app.settings.bo.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;


@Repository
public class SettingsRepository {

    private MongoTemplate mongoTemplate;

    private Settings settings;

    @Autowired
    public SettingsRepository(MongoTemplate mongoTemplate, Settings settings) {
        this.mongoTemplate = mongoTemplate;
        this.settings = settings;
    }

    /**
     * loading settings that are saved in database, and merging into current settings (that are injected)
     */
    @PostConstruct
    void postConstruct(){
        mongoTemplate.findAll(Settings.class).stream()
            .findFirst()
            // if settings are found in database, merging them in the current settings
            .ifPresent(savedSettings -> settings.merge(savedSettings));

    }

    /**
     * Saving settings
     * We don't need any parameter as settings are already in-memory
     */
    public void save(){
        mongoTemplate.remove(Settings.class).all();
        mongoTemplate.save(settings);
    }

}
