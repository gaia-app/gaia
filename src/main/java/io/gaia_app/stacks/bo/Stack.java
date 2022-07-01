package io.gaia_app.stacks.bo;

import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.organizations.Organization;
import io.gaia_app.organizations.User;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @DBRef
    @NotNull
    private TerraformModule module;

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
    private Organization ownerOrganization;

    private BigDecimal estimatedRunningCost;

    private String credentialsId;

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

    public TerraformModule getModule() {
        return module;
    }

    public void setModule(TerraformModule module) {
        this.module = module;
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

    public void setOwnerOrganization(Organization ownerOrganization) {
        this.ownerOrganization = ownerOrganization;
    }

    public Organization getOwnerOrganization() {
        return ownerOrganization;
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

    public String getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(String credentialsId) {
        this.credentialsId = credentialsId;
    }

    /**
     * Builds tfvars file for this stack
     * @return a string with the content of the tfvars file
     */
    public String tfvars() {
        // strings are quoted, other variable types are not
        var stringVariableLine = "%s = \"%s\"\n";
        var variableLine = "%s = %s\n";

        var variablesBuilder = new StringBuilder();

        module.getVariables().forEach(terraformVariable -> {
            var variableName = terraformVariable.getName();
            var variableValue = terraformVariable.getDefaultValue();
            // try getting the value
            if (this.variableValues.containsKey(variableName)) {
                variableValue = this.variableValues.get(variableName);
            }

            if( "string".equals(terraformVariable.getType())){
                variablesBuilder.append(String.format(stringVariableLine, variableName, variableValue));
            }
            else {
                variablesBuilder.append(String.format(variableLine, variableName, variableValue));
            }
        });
        return variablesBuilder.toString();
    }

    public boolean isArchived() {
        return this.state == StackState.ARCHIVED;
    }
}
