package io.codeka.gaia.modules.bo;

import io.codeka.gaia.registries.RegistryDetails;
import io.codeka.gaia.teams.Team;
import io.codeka.gaia.teams.User;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TerraformModule {

    private String id;

    @NotBlank
    private String gitRepositoryUrl;

    private String gitBranch;

    private String directory;

    @Valid
    private List<Variable> variables = new ArrayList<>();

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String cliVersion;

    @DBRef
    private List<Team> authorizedTeams = new ArrayList<>();

    private BigDecimal estimatedMonthlyCost;

    private String estimatedMonthlyCostDescription;

    @DBRef
    private User createdBy;

    private LocalDateTime createdAt = LocalDateTime.now();

    @DBRef
    private User updatedBy;

    private LocalDateTime updatedAt;

    private RegistryDetails registryDetails;

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

    public List<Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public String getCliVersion() {
        return cliVersion;
    }

    public void setCliVersion(String cliVersion) {
        this.cliVersion = cliVersion;
    }

    public List<Team> getAuthorizedTeams() {
        return authorizedTeams;
    }

    public void setAuthorizedTeams(List<Team> authorizedTeams) {
        this.authorizedTeams = authorizedTeams;
    }

    public boolean isAuthorizedFor(User user) {
        return user.isAdmin() || this.authorizedTeams.contains(user.getTeam()) || user.equals(this.createdBy);
    }

    public BigDecimal getEstimatedMonthlyCost() {
        return estimatedMonthlyCost;
    }

    public void setEstimatedMonthlyCost(BigDecimal estimatedMonthlyCost) {
        this.estimatedMonthlyCost = estimatedMonthlyCost;
    }

    public String getEstimatedMonthlyCostDescription() {
        return estimatedMonthlyCostDescription;
    }

    public void setEstimatedMonthlyCostDescription(String estimatedMonthlyCostDescription) {
        this.estimatedMonthlyCostDescription = estimatedMonthlyCostDescription;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public RegistryDetails getRegistryDetails() {
        return registryDetails;
    }

    public void setRegistryDetails(RegistryDetails registryDetails) {
        this.registryDetails = registryDetails;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
