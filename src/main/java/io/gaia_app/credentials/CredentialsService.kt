package io.gaia_app.credentials

import io.gaia_app.encryption.EncryptionService
import io.gaia_app.organizations.User
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Service
import java.util.*

interface CredentialsService {
    /**
     * finds the given credentials from the database.
     * depending on implementation, this method could decrypt credentials.
     */
    fun findById(id: String): Optional<Credentials>
    fun findByIdAndCreatedBy(id: String, createdBy: User): Optional<Credentials>
    fun findAllByCreatedBy(createdBy: User): List<EmptyCredentials>
    fun save(credentials: Credentials): Credentials
    fun deleteById(id: String)

    /**
     * loads the given credentials.
     * depending on implementation, this method could fetch or decrypt the credentials from somewhere.
     * this method should be called by the runner when credentials are needed to run jobs
     */
    fun load(id: String): Optional<Credentials>
}

@Service
@ConditionalOnMissingBean(EncryptionService::class)
class PlainCredentialsService(val credentialsRepository: CredentialsRepository): CredentialsService {

    override fun findById(id: String): Optional<Credentials> = credentialsRepository.findById(id)

    override fun findByIdAndCreatedBy(id: String, createdBy: User): Optional<Credentials> = credentialsRepository.findByIdAndCreatedBy(id, createdBy)

    override fun findAllByCreatedBy(createdBy: User): List<EmptyCredentials> = credentialsRepository.findAllByCreatedBy(createdBy)

    override fun save(credentials: Credentials): Credentials = credentialsRepository.save(credentials)

    override fun deleteById(id: String) = credentialsRepository.deleteById(id)

    override fun load(id: String): Optional<Credentials> = this.findById(id)

}
