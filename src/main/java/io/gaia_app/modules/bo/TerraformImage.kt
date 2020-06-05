package io.gaia_app.modules.bo

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class TerraformImage(
        @field:NotBlank @field:Pattern(regexp = """^[\w][\w.\-\/]{0,127}$""") val repository: String,
        @field:NotBlank val tag: String) {

    fun image() = "$repository:$tag"

    companion object {
        fun defaultInstance() = TerraformImage("hashicorp/terraform", "latest")
    }

}
