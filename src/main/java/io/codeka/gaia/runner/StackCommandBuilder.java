package io.codeka.gaia.runner;

import io.codeka.gaia.bo.Settings;
import io.codeka.gaia.bo.Stack;
import io.codeka.gaia.bo.TerraformModule;
import io.codeka.gaia.bo.backend.Backend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A builder class to create stack commands
 */
@Component
public class StackCommandBuilder {

    private Settings settings;

    @Autowired
    StackCommandBuilder(Settings settings) {
        this.settings = settings;
    }

    /**
     * builds the terraform apply script
     * @return
     */
    String buildApplyScript(Stack stack, TerraformModule module){
        var backendGenCommand = String.format("echo \"terraform {\n" +
                "    backend \\\"http\\\" {\n" +
                "\t\taddress=\\\""+settings.getExternalUrl()+"/api/state/%s\\\"\n" +
                "\t}\n" +
                "}\n\" > backend.tf", stack.getId());

        String applyCommand = buildApplyCommand(stack, module);

        System.out.println(applyCommand);

        var commands = new String[]{
                "set -ex",
                String.format("git clone %s module", module.getGitRepositoryUrl()),
                "cd module",
                "echo 'generating backend configuration'",
                backendGenCommand,
                "cat backend.tf",
                "terraform version",
                "terraform init",
                applyCommand
        };

        // generate a backend.tf config !

        return String.join("\n", commands);
    }

    /**
     * builds the terraform plan script
     * @return
     */
    String buildPlanScript(Stack stack, TerraformModule module){
        var backendGenCommand = String.format("echo \"terraform {\n" +
                "    backend \\\"http\\\" {\n" +
                "\t\taddress=\\\""+settings.getExternalUrl()+"/api/state/%s\\\"\n" +
                "\t}\n" +
                "}\n\" > backend.tf", stack.getId());

        String applyCommand = buildPlanCommand(stack, module);

        System.out.println(applyCommand);

        var commands = new String[]{
                "set -ex",
                String.format("git clone %s module", module.getGitRepositoryUrl()),
                "cd module",
                "echo 'generating backend configuration'",
                backendGenCommand,
                "cat backend.tf",
                "terraform version",
                "terraform init",
                applyCommand
        };

        // generate a backend.tf config !

        return String.join("\n", commands);
    }

    String buildApplyCommand(Stack stack, TerraformModule module) {
        var varFormatString = "-var \"%s=%s\" ";
        var variablesBuilder = new StringBuilder();

        module.getVariables().forEach(terraformVariable -> {

            var name = terraformVariable.getName();
            String value = terraformVariable.getDefaultValue();
            // try getting the value from the stack
            if(stack.getVariableValues().containsKey(name)){
                value = stack.getVariableValues().get(name);
            }
            variablesBuilder.append(String.format(varFormatString, name, value));
        });

        return String.format("terraform apply --auto-approve %s", variablesBuilder.toString());
    }

    String buildPlanCommand(Stack stack, TerraformModule module) {
        var varFormatString = "-var \"%s=%s\" ";
        var variablesBuilder = new StringBuilder();

        module.getVariables().forEach(terraformVariable -> {

            var name = terraformVariable.getName();
            String value = terraformVariable.getDefaultValue();
            // try getting the value from the stack
            if(stack.getVariableValues().containsKey(name)){
                value = stack.getVariableValues().get(name);
            }
            variablesBuilder.append(String.format(varFormatString, name, value));
        });

        return String.format("terraform plan -detailed-exitcode %s", variablesBuilder.toString());
    }

}
