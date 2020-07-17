package io.gaia_app.credentials

import io.gaia_app.teams.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class CredentialsService(val credentialsRepository: CredentialsRepository){

    fun findById(id: String): Optional<Credentials> = this.credentialsRepository.findById(id)

    fun findByIdAndCreatedBy(id: String, createdBy: User) = credentialsRepository.findByIdAndCreatedBy(id, createdBy)

    fun findAllByCreatedBy(createdBy: User) = credentialsRepository.findAllByCreatedBy(createdBy)

    fun save(credentials: Credentials): Credentials = credentialsRepository.save(credentials)

    fun deleteById(id:String) = credentialsRepository.deleteById(id)
}
