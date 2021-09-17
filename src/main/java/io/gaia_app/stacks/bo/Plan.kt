package io.gaia_app.stacks.bo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

/**
 * Represents the structure of terraform plan result.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Plan(val id:String = UUID.randomUUID().toString(),
                val terraform_version:String,
                val resource_changes:List<ResourceChange>) {

    fun getCreateCount() = resource_changes.count { it.change.actions.contains(ChangesTypes.CREATE) }
    fun getUpdateCount() = resource_changes.count { it.change.actions.contains(ChangesTypes.UPDATE) }
    fun getDeleteCount() = resource_changes.count { it.change.actions.contains(ChangesTypes.DELETE) }
    fun getNoOpCount() = resource_changes.count { it.change.actions.contains(ChangesTypes.NOOP) }

    fun isUpToDate() = getCreateCount() == 0 && getUpdateCount() == 0 && getDeleteCount() == 0

}

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
