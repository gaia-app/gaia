package io.codeka.gaia.modules.bo;

import io.codeka.gaia.teams.bo.Team;
import io.codeka.gaia.teams.bo.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TerraformModuleTest {

    @Test
    void module_shouldBeAuthorized_forAdminUser(){
        // given
        var admin = new User("admin");
        var module = new TerraformModule();

        // when
        var authorized = module.isAuthorizedFor(admin);

        // then
        assertTrue(authorized);
    }

    @Test
    void module_shouldBeAuthorized_forAUserOfAnAuthorizedTeam(){
        // given
        var daniel = new User("Daniel Jackson");
        var sg1 = new Team("SG-1");
        daniel.setTeam(sg1);

        var module = new TerraformModule();
        module.setAuthorizedTeams(List.of(sg1));

        // when
        var authorized = module.isAuthorizedFor(daniel);

        // then
        assertTrue(authorized);
    }

    @Test
    void module_shouldBeUnauthorized_forAUserOfAnUnauthorizedTeam(){
        // given
        var daniel = new User("Daniel Jackson");
        var sg1 = new Team("SG-1");
        daniel.setTeam(sg1);

        var module = new TerraformModule();

        // when
        var authorized = module.isAuthorizedFor(daniel);

        // then
        assertFalse(authorized);
    }

}