package io.gaia_app.stacks.bo

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue

/**
 * Represents the structure of terraform plan result.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Plan(val terraform_version:String, val resource_changes:List<ResourceChange>)

/**
 * Represents a resource_changes object in the plan
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class ResourceChange(val address: String, val provider_name: String, val type:String, val change: Change)

/**
 * Represents a change object in the resource_changes object
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Change(val actions: List<ChangesTypes>)

enum class ChangesTypes{
    @JsonProperty("no-op") NOOP,
    @JsonProperty("create") CREATE,
    @JsonProperty("read") READ,
    @JsonProperty("update") UPDATE,
    @JsonProperty("delete") DELETE
}
