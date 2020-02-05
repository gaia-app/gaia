package io.codeka.gaia.runner;

import com.github.mustachejava.Mustache;
import io.codeka.gaia.config.security.StateApiSecurityConfig;
import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.registries.RegistryOAuth2Provider;
import io.codeka.gaia.settings.bo.Settings;
import io.codeka.gaia.stacks.bo.Job;
import io.codeka.gaia.stacks.bo.Stack;
import io.codeka.gaia.stacks.bo.mustache.TerraformScript;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.function.BiFunction;

/**
 * A builder class to create stack commands
 */
@Component
public class StackCommandBuilder {

    private Settings settings;
    private StateApiSecurityConfig.StateApiSecurityProperties stateApiSecurityProperties;
    private Mustache terraformMustache;
    private List<RegistryOAuth2Provider> registryOAuth2Providers;

    @Autowired
    StackCommandBuilder(Settings settings, Mustache terraformMustache, List<RegistryOAuth2Provider> registryOAuth2Providers, StateApiSecurityConfig.StateApiSecurityProperties stateApiSecurityProperties) {
        this.settings = settings;
        this.terraformMustache = terraformMustache;
        this.registryOAuth2Providers = registryOAuth2Providers;
        this.stateApiSecurityProperties = stateApiSecurityProperties;
    }

    /**
     * Returns the url of the git repository filled with OAuth2 token if available
     */
    private String evalGitRepositoryUrl(TerraformModule module) {
        var url = module.getGitRepositoryUrl();
        var data = module.getModuleMetadata().getCreatedBy().getOAuth2User();
        if (data == null) {
            return url;
        }
        return registryOAuth2Providers.stream()
                .filter(p -> p.isAssignableFor(data.getProvider()))
                .map(p -> p.getOAuth2Url(url, data.getToken()))
                .findFirst()
                .orElse(url);
    }

    private String buildScript(Job job, Stack stack, TerraformModule module,
                               BiFunction<Stack, TerraformModule, String> command) {
        var script = new TerraformScript()
                .setExternalUrl(settings.getExternalUrl())
                .setStateApiUser(stateApiSecurityProperties.getUsername())
                .setStateApiPassword(stateApiSecurityProperties.getPassword())
                .setStackId(stack.getId())
                .setGitRepositoryUrl(evalGitRepositoryUrl(module))
                .setTerraformImage(job.getTerraformImage().image());

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
     * builds the terraform plan script
     *
     * @return
     */
    String buildPlanScript(Job job, Stack stack, TerraformModule module) {
        return buildScript(job, stack, module, this::buildPlanCommand);
    }

    /**
     * builds the terraform apply script
     *
     * @return
     */
    String buildApplyScript(Job job, Stack stack, TerraformModule module) {
        return buildScript(job, stack, module, this::buildApplyCommand);
    }

    /**
     * builds the terraform plan script
     *
     * @return
     */
    String buildPlanDestroyScript(Job job, Stack stack, TerraformModule module) {
        return buildScript(job, stack, module, this::buildPlanDestroyCommand);
    }

    /**
     * builds the terraform destroy script
     *
     * @return
     */
    String buildDestroyScript(Job job, Stack stack, TerraformModule module) {
        return buildScript(job, stack, module, this::buildDestroyCommand);
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
     * builds the terraform plan destroy command
     *
     * @param stack
     * @param module
     * @return
     */
    String buildPlanDestroyCommand(Stack stack, TerraformModule module) {
        return buildCommand(stack, module, "terraform plan -destroy -detailed-exitcode");
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
