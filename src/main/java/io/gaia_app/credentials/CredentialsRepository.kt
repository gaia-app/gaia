package io.gaia_app.credentials

import io.gaia_app.organizations.User
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface CredentialsRepository: MongoRepository<Credentials, String> {

    fun findAllByCreatedBy(user: User): List<EmptyCredentials>

    fun findByIdAndCreatedBy(id: String, createdBy: User): Optional<Credentials>
}
