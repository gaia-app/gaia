package io.gaia_app.runner;

import com.github.mustachejava.DefaultMustacheFactory;
import io.gaia_app.config.security.RunnerApiSecurityConfig;
import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.modules.bo.Variable;
import io.gaia_app.registries.RegistryOAuth2Provider;
import io.gaia_app.settings.bo.Settings;
import io.gaia_app.stacks.bo.Job;
import io.gaia_app.stacks.bo.Stack;
import io.gaia_app.organizations.OAuth2User;
import io.gaia_app.organizations.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RunnerCommandBuilderTest {

    @Mock
    RegistryOAuth2Provider registryOAuth2Provider;

    private RunnerCommandBuilder runnerCommandBuilder;

    @BeforeEach
    void setup() {
        var mustache = new DefaultMustacheFactory().compile("mustache/terraform.mustache");

        var stateApiSecurityProperties = new RunnerApiSecurityConfig.RunnerApiSecurityProperties("gaia-backend", "password");

        runnerCommandBuilder = new RunnerCommandBuilder(new Settings(), mustache, List.of(registryOAuth2Provider), stateApiSecurityProperties);
    }

    @Test
    void buildPlanScript_shouldGenerateAFullScript() {
        var module = moduleWithDirectory();
        var stack = new Stack();
        stack.setModule(module);
        var job = new Job();

        var script = runnerCommandBuilder.buildPlanScript(job, stack, module);

        assertTrue(script.contains("echo '[gaia] using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo '[gaia] cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertTrue(script.contains("cd directory"));
        assertTrue(script.contains("echo '[gaia] generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform plan -out plan.binary"));
        assertTrue(script.contains("terraform show -json plan.binary > plan.json"));
    }

    @Test
    void buildPlanScript_shouldGenerateAFullScript_forAModuleWithoutDirectory() {
        var module = moduleWithoutDirectory();
        var stack = new Stack();
        stack.setModule(module);
        var job = new Job();

        var script = runnerCommandBuilder.buildPlanScript(job, stack, module);

        assertTrue(script.contains("echo '[gaia] using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo '[gaia] cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo '[gaia] generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform plan"));
    }

    @Test
    void buildPlanScript_shouldGenerateAFullScript_forAModuleWithAccessToken() {
        var module = moduleWithAccessToken();
        var stack = new Stack();
        stack.setModule(module);
        var job = new Job();

        when(registryOAuth2Provider.isAssignableFor(anyString())).thenReturn(true);
        when(registryOAuth2Provider.getOAuth2Url(anyString(), anyString())).thenReturn("url_with_token");
        var script = runnerCommandBuilder.buildPlanScript(job, stack, module);

        assertTrue(script.contains("echo '[gaia] using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo '[gaia] cloning url_with_token' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone url_with_token"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo '[gaia] generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform plan"));
    }


    @Test
    void buildApplyScript_shouldGenerateAFullScript() {
        var module = moduleWithDirectory();
        var stack = new Stack();
        stack.setModule(module);
        var job = new Job();

        var script = runnerCommandBuilder.buildApplyScript(job, stack, module);

        assertTrue(script.contains("echo '[gaia] using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo '[gaia] cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertTrue(script.contains("cd directory"));
        assertTrue(script.contains("echo '[gaia] generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform apply"));
    }

    @Test
    void buildApplyScript_shouldGenerateAFullScript_forAModuleWithoutDirectory() {
        var module = moduleWithoutDirectory();
        var stack = new Stack();
        stack.setModule(module);
        var job = new Job();

        var script = runnerCommandBuilder.buildApplyScript(job, stack, module);

        assertTrue(script.contains("echo '[gaia] using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo '[gaia] cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo '[gaia] generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform apply -auto-approve"));
    }

    @Test
    void buildApplyScript_shouldGenerateAFullScript_forAModuleWithAccessToken() {
        var module = moduleWithAccessToken();
        var stack = new Stack();
        stack.setModule(module);
        var job = new Job();

        when(registryOAuth2Provider.isAssignableFor(anyString())).thenReturn(true);
        when(registryOAuth2Provider.getOAuth2Url(anyString(), anyString())).thenReturn("url_with_token");
        var script = runnerCommandBuilder.buildApplyScript(job, stack, module);

        assertTrue(script.contains("echo '[gaia] using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo '[gaia] cloning url_with_token' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone url_with_token"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo '[gaia] generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform apply -auto-approve"));
    }


    @Test
    void buildPlanDestroyScript_shouldGenerateAFullScript() {
        var module = moduleWithDirectory();
        var stack = new Stack();
        stack.setModule(module);
        var job = new Job();

        var script = runnerCommandBuilder.buildPlanDestroyScript(job, stack, module);

        assertTrue(script.contains("echo '[gaia] using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo '[gaia] cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertTrue(script.contains("cd directory"));
        assertTrue(script.contains("echo '[gaia] generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform plan -out plan.binary -destroy"));
        assertTrue(script.contains("terraform show -json plan.binary > plan.json"));
    }

    @Test
    void buildPlanDestroyScript_shouldGenerateAFullScript_forAModuleWithoutDirectory() {
        var module = moduleWithoutDirectory();
        var stack = new Stack();
        stack.setModule(module);
        var job = new Job();

        var script = runnerCommandBuilder.buildPlanDestroyScript(job, stack, module);

        assertTrue(script.contains("echo '[gaia] using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo '[gaia] cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo '[gaia] generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform plan -out plan.binary -destroy"));
        assertTrue(script.contains("terraform show -json plan.binary > plan.json"));
    }

    @Test
    void buildPlanDestroyScript_shouldGenerateAFullScript_forAModuleWithAccessToken() {
        var module = moduleWithAccessToken();
        var stack = new Stack();
        stack.setModule(module);
        var job = new Job();

        when(registryOAuth2Provider.isAssignableFor(anyString())).thenReturn(true);
        when(registryOAuth2Provider.getOAuth2Url(anyString(), anyString())).thenReturn("url_with_token");
        var script = runnerCommandBuilder.buildPlanDestroyScript(job, stack, module);

        assertTrue(script.contains("echo '[gaia] using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo '[gaia] cloning url_with_token' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone url_with_token"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo '[gaia] generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform plan -out plan.binary -destroy"));
        assertTrue(script.contains("terraform show -json plan.binary > plan.json"));
    }


    @Test
    void buildDestroyScript_shouldGenerateAFullScript() {
        var module = moduleWithDirectory();
        var stack = new Stack();
        stack.setModule(module);
        var job = new Job();

        var script = runnerCommandBuilder.buildDestroyScript(job, stack, module);

        assertTrue(script.contains("echo '[gaia] using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo '[gaia] cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertTrue(script.contains("cd directory"));
        assertTrue(script.contains("echo '[gaia] generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform destroy -auto-approve"));
    }

    @Test
    void buildDestroyScript_shouldGenerateAFullScript_forAModuleWithoutDirectory() {
        var module = moduleWithoutDirectory();
        var stack = new Stack();
        stack.setModule(module);
        var job = new Job();

        var script = runnerCommandBuilder.buildDestroyScript(job, stack, module);

        assertTrue(script.contains("echo '[gaia] using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo '[gaia] cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo '[gaia] generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform destroy -auto-approve"));
    }

    @Test
    void buildDestroyScript_shouldGenerateAFullScript_forAModuleWithAccessToken() {
        var module = moduleWithAccessToken();
        var stack = new Stack();
        stack.setModule(module);
        var job = new Job();

        when(registryOAuth2Provider.isAssignableFor(anyString())).thenReturn(true);
        when(registryOAuth2Provider.getOAuth2Url(anyString(), anyString())).thenReturn("url_with_token");
        var script = runnerCommandBuilder.buildDestroyScript(job, stack, module);

        assertTrue(script.contains("echo '[gaia] using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo '[gaia] cloning url_with_token' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone url_with_token"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo '[gaia] generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform destroy -auto-approve"));
    }

    @NotNull
    private TerraformModule moduleWithDirectory() {
        var module = new TerraformModule();
        module.setGitRepositoryUrl("git://test");
        module.setDirectory("directory");

        var variable = new Variable("test");
        variable.setDefaultValue("defaultValue");
        module.setVariables(List.of(variable));

        module.getModuleMetadata().setCreatedBy(new User("test_user", null));

        return module;
    }

    @NotNull
    private TerraformModule moduleWithoutDirectory() {
        var module = new TerraformModule();
        module.setGitRepositoryUrl("git://test");

        var variable = new Variable("test");
        variable.setDefaultValue("defaultValue");
        module.setVariables(List.of(variable));

        module.getModuleMetadata().setCreatedBy(new User("test_user", null));

        return module;
    }

    @NotNull
    private TerraformModule moduleWithAccessToken() {
        var module = new TerraformModule();
        module.setGitRepositoryUrl("git://test");

        var variable = new Variable("test");
        variable.setDefaultValue("defaultValue");
        module.setVariables(List.of(variable));

        var user = new User("test_user", null);
        var oAuth2User = new OAuth2User("test_provider", "test_token", null);
        user.setOAuth2User(oAuth2User);
        module.getModuleMetadata().setCreatedBy(user);

        return module;
    }
}
