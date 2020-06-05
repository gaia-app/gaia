package io.gaia_app.modules.repository

import io.gaia_app.modules.bo.TerraformModule
import io.gaia_app.teams.Team
import io.gaia_app.teams.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TerraformModuleRepository : MongoRepository<TerraformModule, String> {
    fun countByModuleMetadataCreatedBy(user: User): Long
    fun countByAuthorizedTeamsContainingOrModuleMetadataCreatedBy(team: Team, user: User): Long
    fun findAllByModuleMetadataCreatedByOrAuthorizedTeamsContaining(user: User, team: Team): List<TerraformModule>
    fun findAllByModuleMetadataCreatedBy(user: User): List<TerraformModule>
}
