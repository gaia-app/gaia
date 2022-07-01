package io.gaia_app.modules.repository

import io.gaia_app.modules.bo.TerraformModule
import io.gaia_app.organizations.Organization
import io.gaia_app.organizations.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TerraformModuleRepository : MongoRepository<TerraformModule, String> {
    fun countByModuleMetadataCreatedBy(user: User): Long
    fun countByAuthorizedOrganizationsContainingOrModuleMetadataCreatedBy(organization: Organization, user: User): Long
    fun findAllByModuleMetadataCreatedByOrAuthorizedOrganizationsContaining(user: User, organization: Organization): List<TerraformModule>
    fun findAllByModuleMetadataCreatedBy(user: User): List<TerraformModule>
}
