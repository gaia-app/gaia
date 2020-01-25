package io.codeka.gaia.modules.repository;

import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.teams.Team;
import io.codeka.gaia.teams.User;
import io.codeka.gaia.test.MongoContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Testcontainers
@DirtiesContext
class TerraformModuleRepositoryIT {

    @Container
    private static MongoContainer mongoContainer = new MongoContainer();

    @Autowired
    private TerraformModuleRepository terraformModuleRepository;

    private User bob;

    @BeforeEach
    void setUp() {
        // sample teams
        Team team1 = new Team("team1");
        Team team2 = new Team("team2");

        // sample owners
        bob = new User("Bob", null);

        // saving sample modules
        TerraformModule module1 = new TerraformModule();
        module1.setId("Module 1");
        module1.setAuthorizedTeams(List.of(team1));
        module1.getModuleMetadata().setCreatedBy(bob);

        TerraformModule module2 = new TerraformModule();
        module2.setId("Module 2");
        module2.setAuthorizedTeams(List.of(team1, team2));
        module2.getModuleMetadata().setCreatedBy(bob);

        terraformModuleRepository.deleteAll();
        terraformModuleRepository.saveAll(List.of(module1, module2));
    }

    @Test
    void bob_shouldSeeItsModules(){
        var modules = terraformModuleRepository.findAllByModuleMetadataCreatedBy(bob);

        assertThat(modules).hasSize(2);
    }

}