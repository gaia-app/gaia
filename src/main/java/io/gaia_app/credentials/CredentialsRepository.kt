package io.gaia_app.credentials

import io.gaia_app.teams.User
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface CredentialsRepository: MongoRepository<Credentials, String> {

    fun findAllByCreatedBy(user: User): List<Credentials>

    fun findByIdAndCreatedBy(id: String, createdBy: User): Optional<Credentials>
}
