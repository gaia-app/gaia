package io.gaia_app.modules.bo

import io.gaia_app.organizations.User
import org.springframework.data.mongodb.core.mapping.DBRef
import java.time.LocalDateTime

data class ModuleMetadata @JvmOverloads constructor(
        val createdAt: LocalDateTime = LocalDateTime.now(),
        @field:DBRef var createdBy: User? = null,
        var updatedAt: LocalDateTime? = null,
        @field:DBRef var updatedBy: User? = null
)
