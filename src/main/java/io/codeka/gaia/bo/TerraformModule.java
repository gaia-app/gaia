package io.codeka.gaia.bo;

import java.util.List;

public class TerraformModule {

    private String id;

    private String gitRepositoryUrl;

    private String gitBranch;

    private String directory;

    private List<TerraformVariable> variables;
    private String name;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGitRepositoryUrl() {
        return gitRepositoryUrl;
    }

    public void setGitRepositoryUrl(String gitRepositoryUrl) {
        this.gitRepositoryUrl = gitRepositoryUrl;
    }

    public String getGitBranch() {
        return gitBranch;
    }

    public void setGitBranch(String gitBranch) {
        this.gitBranch = gitBranch;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public List<TerraformVariable> getVariables() {
        return variables;
    }

    public void setVariables(List<TerraformVariable> variables) {
        this.variables = variables;
    }
}
