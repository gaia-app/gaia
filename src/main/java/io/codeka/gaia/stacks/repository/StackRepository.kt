package io.codeka.gaia.stacks.repository

import io.codeka.gaia.stacks.bo.Stack
import io.codeka.gaia.stacks.bo.StackState
import io.codeka.gaia.teams.Team
import io.codeka.gaia.teams.User
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
