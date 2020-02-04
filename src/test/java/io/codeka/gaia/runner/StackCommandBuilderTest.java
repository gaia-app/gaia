package io.codeka.gaia.runner;

import com.github.mustachejava.DefaultMustacheFactory;
import io.codeka.gaia.config.security.StateApiSecurityConfig;
import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.modules.bo.Variable;
import io.codeka.gaia.registries.RegistryOAuth2Provider;
import io.codeka.gaia.settings.bo.Settings;
import io.codeka.gaia.stacks.bo.Job;
import io.codeka.gaia.stacks.bo.Stack;
import io.codeka.gaia.teams.OAuth2User;
import io.codeka.gaia.teams.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

        var stateApiSecurityProperties = new StateApiSecurityConfig.StateApiSecurityProperties("gaia-backend", "password");

        stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache, List.of(registryOAuth2Provider), stateApiSecurityProperties);
    }

    @Test
    void buildPlanCommand_shouldGenerateASimpleApplyCommand() {
        var module = new TerraformModule();
        module.setVariables(Collections.emptyList());

        var stack = new Stack();

        var command = stackCommandBuilder.buildPlanCommand(stack, module);

        assertEquals("terraform plan -detailed-exitcode ", command);
    }

    @Test
    void buildPlanCommand_shouldGenerateASingleVariableApplyCommand() {
        var module = new TerraformModule();
        var variable = new Variable("test");
        module.setVariables(List.of(variable));

        var stack = new Stack();
        stack.setVariableValues(Map.of("test", "value"));

        var command = stackCommandBuilder.buildPlanCommand(stack, module);

        assertEquals("terraform plan -detailed-exitcode -var \"test=value\" ", command);
    }

    @Test
    void buildPlanCommand_shouldGenerateAMultipleVariableApplyCommand() {
        var module = new TerraformModule();
        var variable = new Variable("test");
        var variable2 = new Variable("test2");
        module.setVariables(List.of(variable, variable2));

        var stack = new Stack();
        stack.setVariableValues(Map.of("test", "value", "test2", "value2"));

        var command = stackCommandBuilder.buildPlanCommand(stack, module);

        assertEquals("terraform plan -detailed-exitcode -var \"test=value\" -var \"test2=value2\" ", command);
    }

    @Test
    void buildPlanCommand_shouldUseDefaultVariableValues() {
        var module = new TerraformModule();
        var variable = new Variable("test");
        variable.setDefaultValue("defaultValue");
        module.setVariables(List.of(variable));

        var stack = new Stack();
        stack.setVariableValues(Collections.emptyMap());

        var command = stackCommandBuilder.buildPlanCommand(stack, module);

        assertEquals("terraform plan -detailed-exitcode -var \"test=defaultValue\" ", command);
    }

    @Test
    void buildPlanScript_shouldGenerateAFullScript() {
        var module = moduleWithDirectory();
        var stack = new Stack();
        var job = new Job();

        var script = stackCommandBuilder.buildPlanScript(job, stack, module);

        assertTrue(script.contains("echo 'using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo 'cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertTrue(script.contains("cd directory"));
        assertTrue(script.contains("echo 'generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform plan"));
    }

    @Test
    void buildPlanScript_shouldGenerateAFullScript_forAModuleWithoutDirectory() {
        var module = moduleWithoutDirectory();
        var stack = new Stack();
        var job = new Job();

        var script = stackCommandBuilder.buildPlanScript(job, stack, module);

        assertTrue(script.contains("echo 'using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo 'cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo 'generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform plan"));
    }

    @Test
    void buildPlanScript_shouldGenerateAFullScript_forAModuleWithAccessToken() {
        var module = moduleWithAccessToken();
        var stack = new Stack();
        var job = new Job();

        when(registryOAuth2Provider.isAssignableFor(anyString())).thenReturn(true);
        when(registryOAuth2Provider.getOAuth2Url(anyString(), anyString())).thenReturn("url_with_token");
        var script = stackCommandBuilder.buildPlanScript(job, stack, module);

        assertTrue(script.contains("echo 'using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo 'cloning url_with_token' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone url_with_token"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo 'generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform plan"));
    }

    @Test
    void buildApplyCommand_shouldGenerateASimpleApplyCommand() {
        var module = new TerraformModule();
        module.setVariables(Collections.emptyList());

        var stack = new Stack();

        var command = stackCommandBuilder.buildApplyCommand(stack, module);

        assertEquals("terraform apply -auto-approve ", command);
    }

    @Test
    void buildApplyCommand_shouldGenerateASingleVariableApplyCommand() {
        var module = new TerraformModule();
        var variable = new Variable("test");
        module.setVariables(List.of(variable));

        var stack = new Stack();
        stack.setVariableValues(Map.of("test", "value"));

        var command = stackCommandBuilder.buildApplyCommand(stack, module);

        assertEquals("terraform apply -auto-approve -var \"test=value\" ", command);
    }

    @Test
    void buildApplyCommand_shouldGenerateAMultipleVariableApplyCommand() {
        var module = new TerraformModule();
        var variable = new Variable("test");
        var variable2 = new Variable("test2");
        module.setVariables(List.of(variable, variable2));

        var stack = new Stack();
        stack.setVariableValues(Map.of("test", "value", "test2", "value2"));

        var command = stackCommandBuilder.buildApplyCommand(stack, module);

        assertEquals("terraform apply -auto-approve -var \"test=value\" -var \"test2=value2\" ", command);
    }

    @Test
    void buildApplyCommand_shouldUseDefaultVariableValues() {
        var module = new TerraformModule();
        var variable = new Variable("test");
        variable.setDefaultValue("defaultValue");
        module.setVariables(List.of(variable));

        var stack = new Stack();
        stack.setVariableValues(Collections.emptyMap());

        var command = stackCommandBuilder.buildApplyCommand(stack, module);

        assertEquals("terraform apply -auto-approve -var \"test=defaultValue\" ", command);
    }

    @Test
    void buildApplyScript_shouldGenerateAFullScript() {
        var module = moduleWithDirectory();
        var stack = new Stack();
        var job = new Job();

        var script = stackCommandBuilder.buildApplyScript(job, stack, module);

        assertTrue(script.contains("echo 'using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo 'cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertTrue(script.contains("cd directory"));
        assertTrue(script.contains("echo 'generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform apply"));
    }

    @Test
    void buildApplyScript_shouldGenerateAFullScript_forAModuleWithoutDirectory() {
        var module = moduleWithoutDirectory();
        var stack = new Stack();
        var job = new Job();

        var script = stackCommandBuilder.buildApplyScript(job, stack, module);

        assertTrue(script.contains("echo 'using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo 'cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo 'generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform apply"));
    }

    @Test
    void buildApplyScript_shouldGenerateAFullScript_forAModuleWithAccessToken() {
        var module = moduleWithAccessToken();
        var stack = new Stack();
        var job = new Job();

        when(registryOAuth2Provider.isAssignableFor(anyString())).thenReturn(true);
        when(registryOAuth2Provider.getOAuth2Url(anyString(), anyString())).thenReturn("url_with_token");
        var script = stackCommandBuilder.buildApplyScript(job, stack, module);

        assertTrue(script.contains("echo 'using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo 'cloning url_with_token' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone url_with_token"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo 'generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform apply"));
    }

    @Test
    void buildPlanDestroyCommand_shouldGenerateASimpleApplyCommand() {
        var module = new TerraformModule();
        module.setVariables(Collections.emptyList());

        var stack = new Stack();

        var command = stackCommandBuilder.buildPlanDestroyCommand(stack, module);

        assertEquals("terraform plan -destroy -detailed-exitcode ", command);
    }

    @Test
    void buildPlanDestroyCommand_shouldGenerateASingleVariableApplyCommand() {
        var module = new TerraformModule();
        var variable = new Variable("test");
        module.setVariables(List.of(variable));

        var stack = new Stack();
        stack.setVariableValues(Map.of("test", "value"));

        var command = stackCommandBuilder.buildPlanDestroyCommand(stack, module);

        assertEquals("terraform plan -destroy -detailed-exitcode -var \"test=value\" ", command);
    }

    @Test
    void buildPlanDestroyCommand_shouldGenerateAMultipleVariableApplyCommand() {
        var module = new TerraformModule();
        var variable = new Variable("test");
        var variable2 = new Variable("test2");
        module.setVariables(List.of(variable, variable2));

        var stack = new Stack();
        stack.setVariableValues(Map.of("test", "value", "test2", "value2"));

        var command = stackCommandBuilder.buildPlanDestroyCommand(stack, module);

        assertEquals("terraform plan -destroy -detailed-exitcode -var \"test=value\" -var \"test2=value2\" ", command);
    }

    @Test
    void buildPlanDestroyCommand_shouldUseDefaultVariableValues() {
        var module = new TerraformModule();
        var variable = new Variable("test");
        variable.setDefaultValue("defaultValue");
        module.setVariables(List.of(variable));

        var stack = new Stack();
        stack.setVariableValues(Collections.emptyMap());

        var command = stackCommandBuilder.buildPlanDestroyCommand(stack, module);

        assertEquals("terraform plan -destroy -detailed-exitcode -var \"test=defaultValue\" ", command);
    }

    @Test
    void buildPlanDestroyScript_shouldGenerateAFullScript() {
        var module = moduleWithDirectory();
        var stack = new Stack();
        var job = new Job();

        var script = stackCommandBuilder.buildPlanDestroyScript(job, stack, module);

        assertTrue(script.contains("echo 'using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo 'cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertTrue(script.contains("cd directory"));
        assertTrue(script.contains("echo 'generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform plan -destroy"));
    }

    @Test
    void buildPlanDestroyScript_shouldGenerateAFullScript_forAModuleWithoutDirectory() {
        var module = moduleWithoutDirectory();
        var stack = new Stack();
        var job = new Job();

        var script = stackCommandBuilder.buildPlanDestroyScript(job, stack, module);

        assertTrue(script.contains("echo 'using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo 'cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo 'generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform plan -destroy"));
    }

    @Test
    void buildPlanDestroyScript_shouldGenerateAFullScript_forAModuleWithAccessToken() {
        var module = moduleWithAccessToken();
        var stack = new Stack();
        var job = new Job();

        when(registryOAuth2Provider.isAssignableFor(anyString())).thenReturn(true);
        when(registryOAuth2Provider.getOAuth2Url(anyString(), anyString())).thenReturn("url_with_token");
        var script = stackCommandBuilder.buildPlanDestroyScript(job, stack, module);

        assertTrue(script.contains("echo 'using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo 'cloning url_with_token' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone url_with_token"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo 'generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform plan -destroy"));
    }

    @Test
    void buildDestroyCommand_shouldGenerateASimpleApplyCommand() {
        var module = new TerraformModule();
        module.setVariables(Collections.emptyList());

        var stack = new Stack();

        var command = stackCommandBuilder.buildDestroyCommand(stack, module);

        assertEquals("terraform destroy -auto-approve ", command);
    }

    @Test
    void buildDestroyCommand_shouldGenerateASingleVariableApplyCommand() {
        var module = new TerraformModule();
        var variable = new Variable("test");
        module.setVariables(List.of(variable));

        var stack = new Stack();
        stack.setVariableValues(Map.of("test", "value"));

        var command = stackCommandBuilder.buildDestroyCommand(stack, module);

        assertEquals("terraform destroy -auto-approve -var \"test=value\" ", command);
    }

    @Test
    void buildDestroyCommand_shouldGenerateAMultipleVariableApplyCommand() {
        var module = new TerraformModule();
        var variable = new Variable("test");
        var variable2 = new Variable("test2");
        module.setVariables(List.of(variable, variable2));

        var stack = new Stack();
        stack.setVariableValues(Map.of("test", "value", "test2", "value2"));

        var command = stackCommandBuilder.buildDestroyCommand(stack, module);

        assertEquals("terraform destroy -auto-approve -var \"test=value\" -var \"test2=value2\" ", command);
    }

    @Test
    void buildDestroyCommand_shouldUseDefaultVariableValues() {
        var module = new TerraformModule();
        var variable = new Variable("test");
        variable.setDefaultValue("defaultValue");
        module.setVariables(List.of(variable));

        var stack = new Stack();
        stack.setVariableValues(Collections.emptyMap());

        var command = stackCommandBuilder.buildDestroyCommand(stack, module);

        assertEquals("terraform destroy -auto-approve -var \"test=defaultValue\" ", command);
    }

    @Test
    void buildDestroyScript_shouldGenerateAFullScript() {
        var module = moduleWithDirectory();
        var stack = new Stack();
        var job = new Job();

        var script = stackCommandBuilder.buildDestroyScript(job, stack, module);

        assertTrue(script.contains("echo 'using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo 'cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertTrue(script.contains("cd directory"));
        assertTrue(script.contains("echo 'generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform destroy"));
    }

    @Test
    void buildDestroyScript_shouldGenerateAFullScript_forAModuleWithoutDirectory() {
        var module = moduleWithoutDirectory();
        var stack = new Stack();
        var job = new Job();

        var script = stackCommandBuilder.buildDestroyScript(job, stack, module);

        assertTrue(script.contains("echo 'using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo 'cloning git://test' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo 'generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform destroy"));
    }

    @Test
    void buildDestroyScript_shouldGenerateAFullScript_forAModuleWithAccessToken() {
        var module = moduleWithAccessToken();
        var stack = new Stack();
        var job = new Job();

        when(registryOAuth2Provider.isAssignableFor(anyString())).thenReturn(true);
        when(registryOAuth2Provider.getOAuth2Url(anyString(), anyString())).thenReturn("url_with_token");
        var script = stackCommandBuilder.buildDestroyScript(job, stack, module);

        assertTrue(script.contains("echo 'using image hashicorp/terraform:latest'"));
        assertTrue(script.contains("echo 'cloning url_with_token' | awk '{ sub(/oauth2:(.*)@/, \"oauth2:[MASKED]@\");}1'"));
        assertTrue(script.contains("git clone url_with_token"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo 'generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform destroy"));
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