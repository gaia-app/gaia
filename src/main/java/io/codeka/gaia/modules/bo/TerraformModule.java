package io.codeka.gaia.modules.bo;

import io.codeka.gaia.registries.RegistryDetails;
import io.codeka.gaia.teams.Team;
import io.codeka.gaia.teams.User;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
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

    @Valid
    private TerraformImage terraformImage = TerraformImage.Companion.defaultInstance();

    @DBRef
    private List<Team> authorizedTeams = new ArrayList<>();

    private BigDecimal estimatedMonthlyCost;

    private String estimatedMonthlyCostDescription;

    private ModuleMetadata moduleMetadata = new ModuleMetadata();

    private RegistryDetails registryDetails;

    private String mainProvider;

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

    public TerraformImage getTerraformImage() {
        return terraformImage;
    }

    public void setTerraformImage(TerraformImage terraformImage) {
        this.terraformImage = terraformImage;
    }

    public List<Team> getAuthorizedTeams() {
        return authorizedTeams;
    }

    public void setAuthorizedTeams(List<Team> authorizedTeams) {
        this.authorizedTeams = authorizedTeams;
    }

    public boolean isAuthorizedFor(User user) {
        return user.isAdmin() || this.authorizedTeams.contains(user.getTeam()) || user.equals(this.moduleMetadata.getCreatedBy());
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

    public RegistryDetails getRegistryDetails() {
        return registryDetails;
    }

    public void setRegistryDetails(RegistryDetails registryDetails) {
        this.registryDetails = registryDetails;
    }

    public ModuleMetadata getModuleMetadata() {
        return moduleMetadata;
    }

    public void setModuleMetadata(ModuleMetadata moduleMetadata) {
        this.moduleMetadata = moduleMetadata;
    }

    public String getMainProvider() {
        return mainProvider;
    }

    public void setMainProvider(String mainProvider) {
        this.mainProvider = mainProvider;
    }
}
