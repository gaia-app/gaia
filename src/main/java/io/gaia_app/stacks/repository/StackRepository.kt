package io.gaia_app.stacks.repository

import io.gaia_app.stacks.bo.Stack
import io.gaia_app.stacks.bo.StackState
import io.gaia_app.teams.Team
import io.gaia_app.teams.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository interface for stacks
 */
@Repository
interface StackRepository : MongoRepository<Stack, String> {
    fun countStacksByStateAndOwnerTeam(state: StackState, team: Team): Long
    fun countStacksByStateAndCreatedBy(state: StackState, user: User): Long
    fun countStacksByState(state: StackState): Long
    fun findByCreatedBy(userWithNoTeam: User): List<Stack>
    fun findByOwnerTeam(team: Team): List<Stack>
    fun findByIdAndOwnerTeam(id: String, team: Team): Optional<Stack>
}
