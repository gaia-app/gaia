package io.codeka.gaia.runner;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.modules.bo.TerraformVariable;
import io.codeka.gaia.settings.bo.Settings;
import io.codeka.gaia.stacks.bo.Stack;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StackCommandBuilderTest {

    private Mustache mustache;

    @BeforeEach
    void setup() {
        var factory = new DefaultMustacheFactory();
        mustache = factory.compile("mustache/terraform.mustache");
    }

    @Test
    void buildPlanCommand_shouldGenerateASimpleApplyCommand() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        var module = new TerraformModule();
        module.setVariables(Collections.emptyList());

        var stack = new Stack();

        var command = stackCommandBuilder.buildPlanCommand(stack, module);

        assertEquals("terraform plan -detailed-exitcode ", command);
    }

    @Test
    void buildPlanCommand_shouldGenerateASingleVariableApplyCommand() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        var module = new TerraformModule();
        var variable = new TerraformVariable();
        variable.setName("test");
        module.setVariables(List.of(variable));

        var stack = new Stack();
        stack.setVariableValues(Map.of("test", "value"));

        var command = stackCommandBuilder.buildPlanCommand(stack, module);

        assertEquals("terraform plan -detailed-exitcode -var \"test=value\" ", command);
    }

    @Test
    void buildPlanCommand_shouldGenerateAMultipleVariableApplyCommand() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        var module = new TerraformModule();
        var variable = new TerraformVariable();
        variable.setName("test");
        var variable2 = new TerraformVariable();
        variable2.setName("test2");
        module.setVariables(List.of(variable, variable2));

        var stack = new Stack();
        stack.setVariableValues(Map.of("test", "value", "test2", "value2"));

        var command = stackCommandBuilder.buildPlanCommand(stack, module);

        assertEquals("terraform plan -detailed-exitcode -var \"test=value\" -var \"test2=value2\" ", command);
    }

    @Test
    void buildPlanCommand_shouldUseDefaultVariableValues() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        var module = new TerraformModule();
        var variable = new TerraformVariable();
        variable.setName("test");
        variable.setDefaultValue("defaultValue");
        module.setVariables(List.of(variable));

        var stack = new Stack();
        stack.setVariableValues(Collections.emptyMap());

        var command = stackCommandBuilder.buildPlanCommand(stack, module);

        assertEquals("terraform plan -detailed-exitcode -var \"test=defaultValue\" ", command);
    }

    @Test
    void buildPlanScript_shouldGenerateAFullScript() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        TerraformModule module = moduleWithDirectory();

        var stack = new Stack();
        var script = stackCommandBuilder.buildPlanScript(stack, module);

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
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        TerraformModule module = moduleWithoutDirectory();

        var stack = new Stack();
        var script = stackCommandBuilder.buildPlanScript(stack, module);

        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo 'generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform plan"));
    }

    @Test
    void buildApplyCommand_shouldGenerateASimpleApplyCommand() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        var module = new TerraformModule();
        module.setVariables(Collections.emptyList());

        var stack = new Stack();

        var command = stackCommandBuilder.buildApplyCommand(stack, module);

        assertEquals("terraform apply -auto-approve ", command);
    }

    @Test
    void buildApplyCommand_shouldGenerateASingleVariableApplyCommand() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        var module = new TerraformModule();
        var variable = new TerraformVariable();
        variable.setName("test");
        module.setVariables(List.of(variable));

        var stack = new Stack();
        stack.setVariableValues(Map.of("test", "value"));

        var command = stackCommandBuilder.buildApplyCommand(stack, module);

        assertEquals("terraform apply -auto-approve -var \"test=value\" ", command);
    }

    @Test
    void buildApplyCommand_shouldGenerateAMultipleVariableApplyCommand() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        var module = new TerraformModule();
        var variable = new TerraformVariable();
        variable.setName("test");
        var variable2 = new TerraformVariable();
        variable2.setName("test2");
        module.setVariables(List.of(variable, variable2));

        var stack = new Stack();
        stack.setVariableValues(Map.of("test", "value", "test2", "value2"));

        var command = stackCommandBuilder.buildApplyCommand(stack, module);

        assertEquals("terraform apply -auto-approve -var \"test=value\" -var \"test2=value2\" ", command);
    }

    @Test
    void buildApplyCommand_shouldUseDefaultVariableValues() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        var module = new TerraformModule();
        var variable = new TerraformVariable();
        variable.setName("test");
        variable.setDefaultValue("defaultValue");
        module.setVariables(List.of(variable));

        var stack = new Stack();
        stack.setVariableValues(Collections.emptyMap());

        var command = stackCommandBuilder.buildApplyCommand(stack, module);

        assertEquals("terraform apply -auto-approve -var \"test=defaultValue\" ", command);
    }

    @Test
    void buildApplyScript_shouldGenerateAFullScript() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        TerraformModule module = moduleWithDirectory();

        var stack = new Stack();
        var script = stackCommandBuilder.buildApplyScript(stack, module);

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
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        TerraformModule module = moduleWithoutDirectory();

        var stack = new Stack();
        var script = stackCommandBuilder.buildApplyScript(stack, module);

        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo 'generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform apply"));
    }

    @Test
    void buildPlanDestroyCommand_shouldGenerateASimpleApplyCommand() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        var module = new TerraformModule();
        module.setVariables(Collections.emptyList());

        var stack = new Stack();

        var command = stackCommandBuilder.buildPlanDestroyCommand(stack, module);

        assertEquals("terraform plan -destroy -detailed-exitcode ", command);
    }

    @Test
    void buildPlanDestroyCommand_shouldGenerateASingleVariableApplyCommand() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        var module = new TerraformModule();
        var variable = new TerraformVariable();
        variable.setName("test");
        module.setVariables(List.of(variable));

        var stack = new Stack();
        stack.setVariableValues(Map.of("test", "value"));

        var command = stackCommandBuilder.buildPlanDestroyCommand(stack, module);

        assertEquals("terraform plan -destroy -detailed-exitcode -var \"test=value\" ", command);
    }

    @Test
    void buildPlanDestroyCommand_shouldGenerateAMultipleVariableApplyCommand() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        var module = new TerraformModule();
        var variable = new TerraformVariable();
        variable.setName("test");
        var variable2 = new TerraformVariable();
        variable2.setName("test2");
        module.setVariables(List.of(variable, variable2));

        var stack = new Stack();
        stack.setVariableValues(Map.of("test", "value", "test2", "value2"));

        var command = stackCommandBuilder.buildPlanDestroyCommand(stack, module);

        assertEquals("terraform plan -destroy -detailed-exitcode -var \"test=value\" -var \"test2=value2\" ", command);
    }

    @Test
    void buildPlanDestroyCommand_shouldUseDefaultVariableValues() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        var module = new TerraformModule();
        var variable = new TerraformVariable();
        variable.setName("test");
        variable.setDefaultValue("defaultValue");
        module.setVariables(List.of(variable));

        var stack = new Stack();
        stack.setVariableValues(Collections.emptyMap());

        var command = stackCommandBuilder.buildPlanDestroyCommand(stack, module);

        assertEquals("terraform plan -destroy -detailed-exitcode -var \"test=defaultValue\" ", command);
    }

    @Test
    void buildPlanDestroyScript_shouldGenerateAFullScript() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        TerraformModule module = moduleWithDirectory();

        var stack = new Stack();
        var script = stackCommandBuilder.buildPlanDestroyScript(stack, module);

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
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        TerraformModule module = moduleWithoutDirectory();

        var stack = new Stack();
        var script = stackCommandBuilder.buildPlanDestroyScript(stack, module);

        assertTrue(script.contains("git clone git://test module"));
        assertTrue(script.contains("cd module"));
        assertFalse(script.contains("cd directory"));
        assertTrue(script.contains("echo 'generating backend configuration'"));
        assertTrue(script.contains("terraform version"));
        assertTrue(script.contains("terraform init"));
        assertTrue(script.contains("terraform plan -destroy"));
    }

    @Test
    void buildDestroyCommand_shouldGenerateASimpleApplyCommand() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        var module = new TerraformModule();
        module.setVariables(Collections.emptyList());

        var stack = new Stack();

        var command = stackCommandBuilder.buildDestroyCommand(stack, module);

        assertEquals("terraform destroy -auto-approve ", command);
    }

    @Test
    void buildDestroyCommand_shouldGenerateASingleVariableApplyCommand() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        var module = new TerraformModule();
        var variable = new TerraformVariable();
        variable.setName("test");
        module.setVariables(List.of(variable));

        var stack = new Stack();
        stack.setVariableValues(Map.of("test", "value"));

        var command = stackCommandBuilder.buildDestroyCommand(stack, module);

        assertEquals("terraform destroy -auto-approve -var \"test=value\" ", command);
    }

    @Test
    void buildDestroyCommand_shouldGenerateAMultipleVariableApplyCommand() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        var module = new TerraformModule();
        var variable = new TerraformVariable();
        variable.setName("test");
        var variable2 = new TerraformVariable();
        variable2.setName("test2");
        module.setVariables(List.of(variable, variable2));

        var stack = new Stack();
        stack.setVariableValues(Map.of("test", "value", "test2", "value2"));

        var command = stackCommandBuilder.buildDestroyCommand(stack, module);

        assertEquals("terraform destroy -auto-approve -var \"test=value\" -var \"test2=value2\" ", command);
    }

    @Test
    void buildDestroyCommand_shouldUseDefaultVariableValues() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        var module = new TerraformModule();
        var variable = new TerraformVariable();
        variable.setName("test");
        variable.setDefaultValue("defaultValue");
        module.setVariables(List.of(variable));

        var stack = new Stack();
        stack.setVariableValues(Collections.emptyMap());

        var command = stackCommandBuilder.buildDestroyCommand(stack, module);

        assertEquals("terraform destroy -auto-approve -var \"test=defaultValue\" ", command);
    }

    @Test
    void buildDestroyScript_shouldGenerateAFullScript() {
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        TerraformModule module = moduleWithDirectory();

        var stack = new Stack();
        var script = stackCommandBuilder.buildDestroyScript(stack, module);

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
        var stackCommandBuilder = new StackCommandBuilder(new Settings(), mustache);

        TerraformModule module = moduleWithoutDirectory();

        var stack = new Stack();
        var script = stackCommandBuilder.buildDestroyScript(stack, module);

        assertTrue(script.contains("git clone git://test module"));
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

        var variable = new TerraformVariable();
        variable.setName("test");
        variable.setDefaultValue("defaultValue");
        module.setVariables(List.of(variable));
        return module;
    }

    @NotNull
    private TerraformModule moduleWithoutDirectory() {
        var module = new TerraformModule();
        module.setGitRepositoryUrl("git://test");

        var variable = new TerraformVariable();
        variable.setName("test");
        variable.setDefaultValue("defaultValue");
        module.setVariables(List.of(variable));
        return module;
    }

}