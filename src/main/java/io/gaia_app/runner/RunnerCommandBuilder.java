package io.gaia_app.runner;

import com.github.mustachejava.Mustache;
import io.gaia_app.config.security.RunnerApiSecurityConfig;
import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.registries.RegistryOAuth2Provider;
import io.gaia_app.settings.bo.Settings;
import io.gaia_app.stacks.bo.Job;
import io.gaia_app.stacks.bo.Stack;
import io.gaia_app.stacks.bo.mustache.TerraformScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * A builder class to create stack commands
 */
@Component
public class RunnerCommandBuilder {

    private static final Logger logger = LoggerFactory.getLogger(RunnerCommandBuilder.class);

    private Settings settings;
    private RunnerApiSecurityConfig.RunnerApiSecurityProperties runnerApiSecurityProperties;
    private Mustache terraformMustache;
    private List<RegistryOAuth2Provider> registryOAuth2Providers;

    @Autowired
    RunnerCommandBuilder(Settings settings, Mustache terraformMustache, List<RegistryOAuth2Provider> registryOAuth2Providers, RunnerApiSecurityConfig.RunnerApiSecurityProperties runnerApiSecurityProperties) {
        this.settings = settings;
        this.terraformMustache = terraformMustache;
        this.registryOAuth2Providers = registryOAuth2Providers;
        this.runnerApiSecurityProperties = runnerApiSecurityProperties;
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

    private String buildScript(Job job, Stack stack, TerraformModule module, String command, boolean uploadPlan) {
        var script = new TerraformScript()
                .setExternalUrl(settings.getExternalUrl())
                .setStateApiUser(runnerApiSecurityProperties.getUsername())
                .setStateApiPassword(runnerApiSecurityProperties.getPassword())
                .setStackId(stack.getId())
                .setJobId(job.getId())
                .setGitRepositoryUrl(evalGitRepositoryUrl(module))
                .setTerraformImage(job.getTerraformImage().image())
                .setUploadPlan(uploadPlan);

        if (module.getDirectory() != null && !module.getDirectory().isBlank()) {
            script.setGitDirectory(module.getDirectory());
        }

        script.setCommand(command);

        var writer = new StringWriter();
        try {
            terraformMustache.execute(writer, script).flush();
            return writer.toString();
        } catch (IOException e) {
            logger.error("Unable to generate script : {}", e.getMessage());
        }
        return "";
    }

    /**
     * builds the terraform plan script
     *
     * @return
     */
    String buildPlanScript(Job job, Stack stack, TerraformModule module) {
        return buildScript(job, stack, module, "terraform plan -out plan.binary -detailed-exitcode", true);
    }

    /**
     * builds the terraform apply script
     *
     * @return
     */
    String buildApplyScript(Job job, Stack stack, TerraformModule module) {
        return buildScript(job, stack, module, "terraform apply -auto-approve", false);
    }

    /**
     * builds the terraform plan script
     *
     * @return
     */
    String buildPlanDestroyScript(Job job, Stack stack, TerraformModule module) {
        return buildScript(job, stack, module, "terraform plan -out plan.binary -destroy -detailed-exitcode", true);
    }

    /**
     * builds the terraform destroy script
     *
     * @return
     */
    String buildDestroyScript(Job job, Stack stack, TerraformModule module) {
        return buildScript(job, stack, module, "terraform destroy -auto-approve", false);
    }

}
