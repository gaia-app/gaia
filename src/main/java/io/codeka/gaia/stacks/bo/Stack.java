package io.codeka.gaia.stacks.bo;

import io.codeka.gaia.teams.Team;
import io.codeka.gaia.teams.User;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a terraform modules instances.
 *
 * It references the module it is based on, with all the values of its variables.
 * It also has a backend configuration, and a provider configuration (in terraform terms).
 */
@MandatoryStackVariablesValidation
@RegexStackVariablesValidation
public class Stack {

    /**
     * This stack's id
     */
    private String id;

    /**
     * The id of the referenced module
     */
    @NotBlank
    private String moduleId;

    /**
     * The variable values of the module
     */
    private Map<String, String> variableValues = new HashMap<>();

    /**
     * The name of the stack
     */
    @NotBlank
    private String name;

    /**
     * The description of the stack
     */
    private String description;

    /**
     * The provider spec
     */
    private String providerSpec;

    private StackState state = StackState.NEW;

    @DBRef
    private Team ownerTeam;

    private BigDecimal estimatedRunningCost;

    @DBRef
    private User createdBy;

    private LocalDateTime createdAt;

    @DBRef
    private User updatedBy;

    private LocalDateTime updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public Map<String, String> getVariableValues() {
        return variableValues;
    }

    public void setVariableValues(Map<String, String> variableValues) {
        this.variableValues = variableValues;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProviderSpec() {
        return providerSpec;
    }

    public void setProviderSpec(String providerSpec) {
        this.providerSpec = providerSpec;
    }

    public StackState getState() {
        return state;
    }

    public void setState(StackState state) {
        this.state = state;
    }

    public void setOwnerTeam(Team ownerTeam) {
        this.ownerTeam = ownerTeam;
    }

    public Team getOwnerTeam() {
        return ownerTeam;
    }

    public BigDecimal getEstimatedRunningCost() {
        return estimatedRunningCost;
    }

    public void setEstimatedRunningCost(BigDecimal estimatedRunningCost) {
        this.estimatedRunningCost = estimatedRunningCost;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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