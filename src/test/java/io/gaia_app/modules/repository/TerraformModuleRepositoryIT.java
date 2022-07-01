package io.gaia_app.modules.repository;

import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.organizations.Organization;
import io.gaia_app.organizations.User;
import io.gaia_app.test.SharedMongoContainerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class TerraformModuleRepositoryIT extends SharedMongoContainerTest {

    @Autowired
    private TerraformModuleRepository terraformModuleRepository;

    private User bob;

    @BeforeEach
    void setUp() {
        // sample organizations
        Organization organization1 = new Organization("organization1");
        Organization organization2 = new Organization("organization2");

        // sample owners
        bob = new User("Bob", null);

        // saving sample modules
        TerraformModule module1 = new TerraformModule();
        module1.setId("Module 1");
        module1.setAuthorizedOrganizations(List.of(organization1));
        module1.getModuleMetadata().setCreatedBy(bob);

        TerraformModule module2 = new TerraformModule();
        module2.setId("Module 2");
        module2.setAuthorizedOrganizations(List.of(organization1, organization2));
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
