package io.gaia_app.runner;

import com.github.mustachejava.DefaultMustacheFactory;
import io.gaia_app.config.security.RunnerApiSecurityConfig;
import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.modules.bo.Variable;
import io.gaia_app.registries.RegistryOAuth2Provider;
import io.gaia_app.settings.bo.Settings;
import io.gaia_app.stacks.bo.Job;
import io.gaia_app.stacks.bo.Stack;
import io.gaia_app.teams.OAuth2User;
import io.gaia_app.teams.User;
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
class StackCommandBuilderTest {

    @Mock
    RegistryOAuth2Provider registryOAuth2Provider;

    private StackCommandBuilder stackCommandBuilder;

    @BeforeEach
    void setup() {
        var mustache = new DefaultMustacheFactory().compile("mustache/terraform.mustache");

        var stateApiSecurityProperties = new RunnerApiSecurityConfig.RunnerApiSecurityProperties("gaia-backend", "password");

        stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache, List.of(registryOAuth2Provider), stateApiSecurityProperties);
    }

    @Test
    void buildPlanScript_shouldGenerateAFullScript() {
        var module = moduleWithDirectory();
        var stack = new Stack();
        stack.setModule(module);
        var job = new Job();

        var script = stackCommandBuilder.buildPlanScript(job, stack, module);

        assertTrue(script.contains("echo '[gaia] using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo '[gaia] cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertTrue(script.contains("cd directory"));
        assertTrue(script.contains("echo '[gaia] generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform plan"));
    }

    @Test
    void buildPlanScript_shouldGenerateAFullScript_forAModuleWithoutDirectory() {
        var module = moduleWithoutDirectory();
        var stack = new Stack();
        stack.setModule(module);
        var job = new Job();

        var script = stackCommandBuilder.buildPlanScript(job, stack, module);

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
        var script = stackCommandBuilder.buildPlanScript(job, stack, module);

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

        var script = stackCommandBuilder.buildApplyScript(job, stack, module);

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

        var script = stackCommandBuilder.buildApplyScript(job, stack, module);

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
        var script = stackCommandBuilder.buildApplyScript(job, stack, module);

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

        var script = stackCommandBuilder.buildPlanDestroyScript(job, stack, module);

        assertTrue(script.contains("echo '[gaia] using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo '[gaia] cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertTrue(script.contains("cd directory"));
        assertTrue(script.contains("echo '[gaia] generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform plan -destroy"));
    }

    @Test
    void buildPlanDestroyScript_shouldGenerateAFullScript_forAModuleWithoutDirectory() {
        var module = moduleWithoutDirectory();
        var stack = new Stack();
        stack.setModule(module);
        var job = new Job();

        var script = stackCommandBuilder.buildPlanDestroyScript(job, stack, module);

        assertTrue(script.contains("echo '[gaia] using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo '[gaia] cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo '[gaia] generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform plan -destroy"));
    }

    @Test
    void buildPlanDestroyScript_shouldGenerateAFullScript_forAModuleWithAccessToken() {
        var module = moduleWithAccessToken();
        var stack = new Stack();
        stack.setModule(module);
        var job = new Job();

        when(registryOAuth2Provider.isAssignableFor(anyString())).thenReturn(true);
        when(registryOAuth2Provider.getOAuth2Url(anyString(), anyString())).thenReturn("url_with_token");
        var script = stackCommandBuilder.buildPlanDestroyScript(job, stack, module);

        assertTrue(script.contains("echo '[gaia] using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo '[gaia] cloning url_with_token' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone url_with_token"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo '[gaia] generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform plan -destroy"));
    }


    @Test
    void buildDestroyScript_shouldGenerateAFullScript() {
        var module = moduleWithDirectory();
        var stack = new Stack();
        stack.setModule(module);
        var job = new Job();

        var script = stackCommandBuilder.buildDestroyScript(job, stack, module);

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

        var script = stackCommandBuilder.buildDestroyScript(job, stack, module);

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
        var script = stackCommandBuilder.buildDestroyScript(job, stack, module);

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
