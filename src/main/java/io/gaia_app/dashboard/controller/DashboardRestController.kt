package io.gaia_app.dashboard.controller

import io.gaia_app.modules.repository.TerraformModuleRepository
import io.gaia_app.stacks.bo.StackState
import io.gaia_app.stacks.repository.StackRepository
import io.gaia_app.teams.Team
import io.gaia_app.teams.User
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
    fun summary(user: User, team: Team?) =
        when {
            user.isAdmin -> {
                mapOf(
                    "modulesCount" to moduleRepository.count(),
                    "runningStacksCount" to stackRepository.countStacksByState(StackState.RUNNING),
                    "toUpdateStacksCount" to stackRepository.countStacksByState(StackState.TO_UPDATE))
            }
            team != null -> {
                mapOf(
                    "modulesCount" to moduleRepository.countByAuthorizedTeamsContainingOrModuleMetadataCreatedBy(team, user),
                    "runningStacksCount" to stackRepository.countStacksByStateAndOwnerTeam(StackState.RUNNING, team),
                    "toUpdateStacksCount" to stackRepository.countStacksByStateAndOwnerTeam(StackState.TO_UPDATE, team))
            }
            else -> {
                mapOf(
                    "modulesCount" to moduleRepository.countByModuleMetadataCreatedBy(user),
                    "runningStacksCount" to stackRepository.countStacksByStateAndCreatedBy(StackState.RUNNING, user),
                    "toUpdateStacksCount" to stackRepository.countStacksByStateAndCreatedBy(StackState.TO_UPDATE, user))
            }
        }

}
