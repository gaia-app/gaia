package io.gaia_app.credentials

import com.fasterxml.jackson.annotation.JsonAlias
import io.gaia_app.encryption.EncryptionService
import io.gaia_app.teams.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.vault.core.VaultTemplate
import java.util.*

@Service
class CredentialsService(val credentialsRepository: CredentialsRepository, val encryptionService: EncryptionService) {

    @Autowired(required = false)
    var vaultTemplate: VaultTemplate? = null

    fun findById(id: String): Optional<Credentials> = this.credentialsRepository.findById(id).map { decrypt(it) }

    fun findByIdAndCreatedBy(id: String, createdBy: User): Optional<Credentials> = credentialsRepository.findByIdAndCreatedBy(id, createdBy).map { decrypt(it) }

    fun findAllByCreatedBy(createdBy: User) = credentialsRepository.findAllByCreatedBy(createdBy)

    fun save(credentials: Credentials): Credentials {
        return encrypt(credentials).apply { credentialsRepository.save(credentials) }
    }

    fun deleteById(id: String) = credentialsRepository.deleteById(id)

    fun encrypt(it: Credentials): Credentials = when (it) {
        is AWSCredentials -> encryptionService.encryptAwsCredentials(it)
        is GoogleCredentials -> encryptionService.encryptGoogleCredentials(it)
        is AzureRMCredentials -> encryptionService.encryptAzurermCredentials(it)
        // vault credentials does no need encryption
        is VaultAWSCredentials -> it
    }

    fun decrypt(it: Credentials): Credentials = when (it) {
        is AWSCredentials -> encryptionService.decryptAwsCredentials(it)
        is GoogleCredentials -> encryptionService.decryptGoogleCredentials(it)
        is AzureRMCredentials -> encryptionService.decryptAzurermCredentials(it)
        is VaultAWSCredentials -> loadAWSCredentialsFromVault(it)
    }

    fun loadAWSCredentialsFromVault(vaultAWSCredentials: VaultAWSCredentials): AWSCredentials {
        val path = "${vaultAWSCredentials.vaultAwsSecretEnginePath.trimEnd('/')}/creds/${vaultAWSCredentials.vaultAwsRole}"
        val vaultResponse = vaultTemplate!!.read(path, VaultAWSResponse::class.java)

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
    return azureRMCredentials
}

fun EncryptionService.decryptAzurermCredentials(azureRMCredentials: AzureRMCredentials): Credentials {
    azureRMCredentials.clientId = this.decrypt(azureRMCredentials.clientId)
    azureRMCredentials.clientSecret = this.decrypt(azureRMCredentials.clientSecret)
    return azureRMCredentials
}
