package io.codeka.gaia.bo;

import io.codeka.gaia.teams.bo.Team;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a terraform modules instances.
 *
 * It references the module it is based on, with all the values of its variables.
 * It also has a backend configuration, and a provider configuration (in terraform terms).
 */
public class Stack {

    /**
     * This stack's id
     */
    private String id;

    /**
     * The id of the referenced module
     */
    private String moduleId;

    /**
     * The variable values of the module
     */
    private Map<String, String> variableValues = new HashMap<>();

    /**
     * The name of the stack
     */
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

    public BigDecimal getEstimatedRunningCost() {
        return estimatedRunningCost;
    }

    public void setEstimatedRunningCost(BigDecimal estimatedRunningCost) {
        this.estimatedRunningCost = estimatedRunningCost;
    }
}