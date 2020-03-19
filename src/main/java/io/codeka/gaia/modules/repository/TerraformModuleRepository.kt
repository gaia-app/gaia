package io.codeka.gaia.modules.repository

import io.codeka.gaia.modules.bo.TerraformModule
import io.codeka.gaia.teams.Team
import io.codeka.gaia.teams.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TerraformModuleRepository : MongoRepository<TerraformModule, String> {
    fun countByModuleMetadataCreatedBy(user: User): Long
    fun countByAuthorizedTeamsContainingOrModuleMetadataCreatedBy(team: Team, user: User): Long
    fun findAllByModuleMetadataCreatedByOrAuthorizedTeamsContaining(user: User, team: Team): List<TerraformModule>
    fun findAllByModuleMetadataCreatedBy(user: User): List<TerraformModule>
}
