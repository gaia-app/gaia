package io.codeka.gaia.runner;

import com.github.mustachejava.Mustache;
import io.codeka.gaia.bo.Settings;
import io.codeka.gaia.bo.Stack;
import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.bo.mustache.TerraformScript;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.function.BiFunction;

/**
 * A builder class to create stack commands
 */
@Component
public class StackCommandBuilder {

    private Settings settings;

    private Mustache terraformMustache;

    @Autowired
    StackCommandBuilder(Settings settings, Mustache terraformMustache) {
        this.settings = settings;
        this.terraformMustache = terraformMustache;
    }

    private String buildScript(Stack stack, TerraformModule module,
                               BiFunction<Stack, TerraformModule, String> command) {
        var script = new TerraformScript()
                .setExternalUrl(settings.getExternalUrl())
                .setStackId(stack.getId())
                .setGitRepositoryUrl(module.getGitRepositoryUrl());

        if (StringUtils.isNotBlank(module.getDirectory())) {
            script.setGitDirectory(module.getDirectory());
        }

        script.setCommand(command.apply(stack, module));

        var writer = new StringWriter();
        try {
            terraformMustache.execute(writer, script).flush();
            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    private String buildCommand(Stack stack, TerraformModule module, String command) {
        var varFormatString = "-var \"%s=%s\" ";
        var variablesBuilder = new StringBuilder();

        module.getVariables().forEach(terraformVariable -> {

            var name = terraformVariable.getName();
            String value = terraformVariable.getDefaultValue();
            // try getting the value from the stack
            if (stack.getVariableValues().containsKey(name)) {
                value = stack.getVariableValues().get(name);
            }
            variablesBuilder.append(String.format(varFormatString, name, value));
        });

        return String.format("%s %s", command, variablesBuilder.toString());
    }

    /**
     * builds the terraform apply script
     *
     * @return
     */
    String buildApplyScript(Stack stack, TerraformModule module) {
        return buildScript(stack, module, this::buildApplyCommand);
    }

    /**
     * builds the terraform plan script
     *
     * @return
     */
    String buildPlanScript(Stack stack, TerraformModule module) {
        return buildScript(stack, module, this::buildPlanCommand);
    }

    /**
     * builds the terraform destroy script
     *
     * @return
     */
    String buildDestroyScript(Stack stack, TerraformModule module) {
        return buildScript(stack, module, this::buildDestroyCommand);
    }

    /**
     * builds the terraform apply command
     *
     * @param stack
     * @param module
     * @return
     */
    String buildApplyCommand(Stack stack, TerraformModule module) {
        return buildCommand(stack, module, "terraform apply -auto-approve");
    }

    /**
     * builds the terraform plan command
     *
     * @param stack
     * @param module
     * @return
     */
    String buildPlanCommand(Stack stack, TerraformModule module) {
        return buildCommand(stack, module, "terraform plan -detailed-exitcode");
    }

    /**
     * builds the terraform destroy command
     *
     * @param stack
     * @param module
     * @return
     */
    String buildDestroyCommand(Stack stack, TerraformModule module) {
        return buildCommand(stack, module, "terraform destroy -auto-approve");
    }

}
