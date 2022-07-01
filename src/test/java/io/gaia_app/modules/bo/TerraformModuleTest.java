package io.gaia_app.modules.bo;

import io.gaia_app.organizations.Organization;
import io.gaia_app.organizations.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TerraformModuleTest {

    @Test
    void module_shouldBeAuthorized_forAdminUser(){
        // given
        var admin = new User("admin", null);
        admin.setAdmin(true);
        var module = new TerraformModule();

        // when
        var authorized = module.isAuthorizedFor(admin);

        // then
        assertTrue(authorized);
    }

    @Test
    void module_shouldBeAuthorized_forTheModuleCreator(){
        // given
        var sg1 = new Organization("SG-1");
        var daniel = new User("Daniel Jackson", sg1);

        var module = new TerraformModule();
        module.getModuleMetadata().setCreatedBy(daniel);

        // when
        var authorized = module.isAuthorizedFor(daniel);

        // then
        assertTrue(authorized);
    }


    @Test
    void module_shouldBeAuthorized_forAUserOfAnAuthorizedOrganization(){
        // given
        var sg1 = new Organization("SG-1");
        var daniel = new User("Daniel Jackson", sg1);

        var module = new TerraformModule();
        module.setAuthorizedOrganizations(List.of(sg1));

        // when
        var authorized = module.isAuthorizedFor(daniel);

        // then
        assertTrue(authorized);
    }

    @Test
    void module_shouldBeUnauthorized_forAUserOfAnUnauthorizedOrganization(){
        // given
        var sg1 = new Organization("SG-1");
        var daniel = new User("Daniel Jackson", sg1);

        var module = new TerraformModule();

        // when
        var authorized = module.isAuthorizedFor(daniel);

        // then
        assertFalse(authorized);
    }

}
