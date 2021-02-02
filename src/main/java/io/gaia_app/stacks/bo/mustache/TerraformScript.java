package io.gaia_app.stacks.bo.mustache;

/**
 * Object matching the terraform mustache template
 */
public class TerraformScript {

    private String terraformImage;
    private String gitRepositoryUrl;
    private String gitDirectory;
    private String externalUrl;
    private String stackId;
    private String command;
    private String stateApiUser;
    private String stateApiPassword;
    private boolean uploadPlan;
    private String jobId;

    public String getTerraformImage() {
        return terraformImage;
    }

    public TerraformScript setTerraformImage(String terraformImage) {
        this.terraformImage = terraformImage;
        return this;
    }

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

    public String getStateApiUser() {
        return stateApiUser;
    }

    public TerraformScript setStateApiUser(String stateApiUser) {
        this.stateApiUser = stateApiUser;
        return this;
    }

    public String getStateApiPassword() {
        return stateApiPassword;
    }

    public TerraformScript setStateApiPassword(String stateApiPassword) {
        this.stateApiPassword = stateApiPassword;
        return this;
    }

    public boolean isUploadPlan() {
        return uploadPlan;
    }

    public TerraformScript setUploadPlan(boolean uploadPlan) {
        this.uploadPlan = uploadPlan;
        return this;
    }

    public TerraformScript setJobId(String jobId) {
        this.jobId = jobId;
        return this;
    }

    public String getJobId() {
        return jobId;
    }
}
