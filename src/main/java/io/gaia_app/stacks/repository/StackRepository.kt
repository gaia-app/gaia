package io.gaia_app.stacks.repository

import io.gaia_app.modules.bo.TerraformModule
import io.gaia_app.stacks.bo.Stack
import io.gaia_app.stacks.bo.StackState
import io.gaia_app.organizations.Organization
import io.gaia_app.organizations.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository interface for stacks
 */
@Repository
interface StackRepository : MongoRepository<Stack, String> {
    fun countStacksByStateAndOwnerOrganization(state: StackState, organization: Organization): Long
    fun countStacksByStateAndCreatedBy(state: StackState, user: User): Long
    fun countStacksByState(state: StackState): Long
    fun countStacksByModule(module: TerraformModule): Long
    fun findByCreatedBy(userWithNoOrganization: User): List<Stack>
    fun findByOwnerOrganization(organization: Organization): List<Stack>
    fun findByIdAndOwnerOrganization(id: String, organization: Organization): Optional<Stack>
}
