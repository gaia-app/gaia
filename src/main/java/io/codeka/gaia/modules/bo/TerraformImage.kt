package io.codeka.gaia.modules.bo

import javax.validation.constraints.NotBlank

data class TerraformImage @JvmOverloads constructor(
        @field:NotBlank val repository: String,
        @field:NotBlank val tag: String) {

    fun image() = "$repository:$tag"

    companion object {
        fun defaultInstance() = TerraformImage("hashicorp/terraform", "latest")
    }

}
