package io.codeka.gaia.stacks.bo.mustache;

/**
 * Object matching the terraform mustache template
 */
public class TerraformScript {

    private String gitRepositoryUrl;
    private String gitDirectory;
    private String externalUrl;
    private String stackId;
    private String command;

    public String getGitRepositoryUrl() {
        return gitRepositoryUrl;
    }

    public TerraformScript setGitRepositoryUrl(String gitRepositoryUrl) {
        this.gitRepositoryUrl = gitRepositoryUrl;
        return this;
    }

    public String getGitDirectory() {
        return gitDirectory;
    }

    public TerraformScript setGitDirectory(String gitDirectory) {
        this.gitDirectory = gitDirectory;
        return this;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public TerraformScript setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
        return this;
    }

    public String getStackId() {
        return stackId;
    }

    public TerraformScript setStackId(String stackId) {
        this.stackId = stackId;
        return this;
    }

    public String getCommand() {
        return command;
    }

    public TerraformScript setCommand(String command) {
        this.command = command;
        return this;
    }
}
