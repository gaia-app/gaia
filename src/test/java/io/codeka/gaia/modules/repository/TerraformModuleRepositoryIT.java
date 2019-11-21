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

    private Team team1;

    private Team team2;

    private User bob;

    private TerraformModule module1;

    private TerraformModule module2;

    @BeforeEach
    void setUp() {
        // sample teams
        team1 = new Team("team1");
        team2 = new Team("team2");

        // sample owners
        bob = new User("Bob", null);

        // saving sample modules
        module1 = new TerraformModule();
        module1.setId("Module 1");
        module1.setAuthorizedTeams(List.of(team1));
        module1.setCreatedBy(bob);

        module2 = new TerraformModule();
        module2.setId("Module 2");
        module2.setAuthorizedTeams(List.of(team1, team2));
        module2.setCreatedBy(bob);

        terraformModuleRepository.deleteAll();
        terraformModuleRepository.saveAll(List.of(module1, module2));
    }

    @Test
    void team1Users_shouldHaveAccessToModule1And2(){
        var modules = terraformModuleRepository.findAllByAuthorizedTeamsContainingOrCreatedBy(team1, null);

        assertThat(modules).hasSize(2);
    }

    @Test
    void team2Users_shouldHaveAccessToModule2(){
        var modules = terraformModuleRepository.findAllByAuthorizedTeamsContainingOrCreatedBy(team2, null);

        assertThat(modules).hasSize(1);
    }

}