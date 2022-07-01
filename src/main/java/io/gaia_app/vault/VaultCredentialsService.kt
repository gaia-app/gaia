package io.gaia_app.vault

import com.fasterxml.jackson.annotation.JsonAlias
import io.gaia_app.credentials.*
import io.gaia_app.encryption.EncryptionService
import io.gaia_app.organizations.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.stereotype.Service
import org.springframework.vault.core.VaultTemplate
import java.util.*

@Service
@ConditionalOnBean(VaultEncryptionService::class)
class VaultCredentialsService(val credentialsRepository: CredentialsRepository,
                              val encryptionService: EncryptionService,
                              val vaultTemplate: VaultTemplate) : CredentialsService {

    override fun findById(id: String): Optional<Credentials> = this.credentialsRepository.findById(id).map { decrypt(it) }

    override fun findByIdAndCreatedBy(id: String, createdBy: User): Optional<Credentials> = credentialsRepository.findByIdAndCreatedBy(id, createdBy).map { decrypt(it) }

    override fun findAllByCreatedBy(createdBy: User) = credentialsRepository.findAllByCreatedBy(createdBy)

    override fun save(credentials: Credentials): Credentials {
        return encrypt(credentials).apply { credentialsRepository.save(credentials) }
    }

    override fun deleteById(id: String) = credentialsRepository.deleteById(id)

    override fun load(id: String): Optional<Credentials> = this.credentialsRepository.findById(id).map { load(it) }

    fun encrypt(it: Credentials): Credentials = when (it) {
        // we encrypt only credentials that contains sensitive information
        is AWSCredentials -> encryptionService.encryptAwsCredentials(it)
        is GoogleCredentials -> encryptionService.encryptGoogleCredentials(it)
        is AzureRMCredentials -> encryptionService.encryptAzurermCredentials(it)
        // vault credentials does no need encryption as we only store ids
        is VaultAWSCredentials -> it
    }

    fun decrypt(it: Credentials): Credentials = when (it) {
        // we decrypt only credentials that contains sensitive information
        is AWSCredentials -> encryptionService.decryptAwsCredentials(it)
        is GoogleCredentials -> encryptionService.decryptGoogleCredentials(it)
        is AzureRMCredentials -> encryptionService.decryptAzurermCredentials(it)
        else -> it
    }

    fun load(it: Credentials): Credentials = when (it) {
        // vault credentials need to be fetched from vault, other type simply need decryption
        is VaultAWSCredentials -> loadAWSCredentialsFromVault(it)
        else -> decrypt(it)
    }

    fun loadAWSCredentialsFromVault(vaultAWSCredentials: VaultAWSCredentials): AWSCredentials {
        val path = "${vaultAWSCredentials.vaultAwsSecretEnginePath.trimEnd('/')}/creds/${vaultAWSCredentials.vaultAwsRole}"
        val vaultResponse = vaultTemplate.read(path, VaultAWSResponse::class.java)

        // IAM credentials are eventually consistent with respect to other Amazon services.
        // adding a delay of 5 seconds before returning them
        runBlocking {
            delay(5_000)
        }

        return vaultResponse?.data?.toAWSCredentials() ?: throw RuntimeException("Unable to get AWS credentials from Vault")
    }
}

data class VaultAWSResponse(@JsonAlias("access_key") val accessKey: String, @JsonAlias("secret_key") val secretKey: String) {
    fun toAWSCredentials() = AWSCredentials(accessKey, secretKey)
}

fun EncryptionService.encryptAwsCredentials(awsCredentials: AWSCredentials): Credentials {
    awsCredentials.accessKey = this.encrypt(awsCredentials.accessKey)
    awsCredentials.secretKey = this.encrypt(awsCredentials.secretKey)
    return awsCredentials
}

fun EncryptionService.decryptAwsCredentials(awsCredentials: AWSCredentials): Credentials {
    awsCredentials.accessKey = this.decrypt(awsCredentials.accessKey)
    awsCredentials.secretKey = this.decrypt(awsCredentials.secretKey)
    return awsCredentials
}

fun EncryptionService.encryptGoogleCredentials(googleCredentials: GoogleCredentials): Credentials {
    googleCredentials.serviceAccountJSONContents = this.encrypt(googleCredentials.serviceAccountJSONContents)
    return googleCredentials
}

fun EncryptionService.decryptGoogleCredentials(googleCredentials: GoogleCredentials): Credentials {
    googleCredentials.serviceAccountJSONContents = this.decrypt(googleCredentials.serviceAccountJSONContents)
    return googleCredentials
}

fun EncryptionService.encryptAzurermCredentials(azureRMCredentials: AzureRMCredentials): Credentials {
    azureRMCredentials.clientId = this.encrypt(azureRMCredentials.clientId)
    azureRMCredentials.clientSecret = this.encrypt(azureRMCredentials.clientSecret)
    azureRMCredentials.subscriptionId = this.encrypt(azureRMCredentials.subscriptionId)
    azureRMCredentials.tenantId = this.encrypt(azureRMCredentials.tenantId)
    if (azureRMCredentials.environment != null){
        azureRMCredentials.environment = this.encrypt(azureRMCredentials.environment!!)
    }
    if (azureRMCredentials.backendAccessKey != null){
        azureRMCredentials.backendAccessKey = this.encrypt(azureRMCredentials.backendAccessKey!!)
    }
    return azureRMCredentials
}

fun EncryptionService.decryptAzurermCredentials(azureRMCredentials: AzureRMCredentials): Credentials {
    azureRMCredentials.clientId = this.decrypt(azureRMCredentials.clientId)
    azureRMCredentials.clientSecret = this.decrypt(azureRMCredentials.clientSecret)
    azureRMCredentials.subscriptionId = this.decrypt(azureRMCredentials.subscriptionId)
    azureRMCredentials.tenantId = this.decrypt(azureRMCredentials.tenantId)
    if (azureRMCredentials.environment != null){
        azureRMCredentials.environment = this.decrypt(azureRMCredentials.environment!!)
    }
    if (azureRMCredentials.backendAccessKey != null){
        azureRMCredentials.backendAccessKey = this.decrypt(azureRMCredentials.backendAccessKey!!)
    }
    return azureRMCredentials
}
