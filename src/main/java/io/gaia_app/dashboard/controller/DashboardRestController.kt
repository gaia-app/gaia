package io.gaia_app.dashboard.controller

import io.gaia_app.modules.repository.TerraformModuleRepository
import io.gaia_app.stacks.bo.StackState
import io.gaia_app.stacks.repository.StackRepository
import io.gaia_app.organizations.Organization
import io.gaia_app.organizations.User
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/dashboard")
@Secured
class DashboardRestController(
    private val moduleRepository: TerraformModuleRepository,
    private val stackRepository: StackRepository) {

    @GetMapping("/summary")
    fun summary(user: User, organization: Organization?) =
        when {
            user.isAdmin -> {
                mapOf(
                    "modulesCount" to moduleRepository.count(),
                    "runningStacksCount" to stackRepository.countStacksByState(StackState.RUNNING),
                    "toUpdateStacksCount" to stackRepository.countStacksByState(StackState.TO_UPDATE))
            }
            organization != null -> {
                mapOf(
                    "modulesCount" to moduleRepository.countByAuthorizedOrganizationsContainingOrModuleMetadataCreatedBy(organization, user),
                    "runningStacksCount" to stackRepository.countStacksByStateAndOwnerOrganization(StackState.RUNNING, organization),
                    "toUpdateStacksCount" to stackRepository.countStacksByStateAndOwnerOrganization(StackState.TO_UPDATE, organization))
            }
            else -> {
                mapOf(
                    "modulesCount" to moduleRepository.countByModuleMetadataCreatedBy(user),
                    "runningStacksCount" to stackRepository.countStacksByStateAndCreatedBy(StackState.RUNNING, user),
                    "toUpdateStacksCount" to stackRepository.countStacksByStateAndCreatedBy(StackState.TO_UPDATE, user))
            }
        }

}
