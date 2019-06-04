package io.codeka.gaia.bo;

import io.codeka.gaia.bo.backend.Backend;

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
     * The provider spec
     */
    private String providerSpec;

    private StackState state = StackState.NEW;

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
}
