package io.gaia_app.vault

import io.gaia_app.credentials.AWSCredentials
import io.gaia_app.credentials.AzureRMCredentials
import io.gaia_app.credentials.CredentialsRepository
import io.gaia_app.credentials.GoogleCredentials
import io.gaia_app.encryption.EncryptionService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness
import org.mockito.stubbing.Answer
import org.springframework.vault.core.VaultTemplate
import java.util.*

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class VaultCredentialsServiceTest {

    @Mock
    lateinit var credentialsRepository: CredentialsRepository

    @Mock
    lateinit var encryptionService: EncryptionService

    @Mock
    lateinit var vaultTemplate: VaultTemplate

    @InjectMocks
    lateinit var credentialsService: VaultCredentialsService


    @Test
    fun findAWSCredentials_shouldReturnDecryptedCredentials() {
        val encryptedAwsCredentials = AWSCredentials("encryptedAccess", "encryptedSecret")
        val plainAWSCredentials = AWSCredentials("access", "secret")

        `when`(encryptionService.decrypt("encryptedAccess")).thenReturn("access")
        `when`(encryptionService.decrypt("encryptedSecret")).thenReturn("secret")
        `when`(credentialsRepository.findById("aws")).thenReturn(Optional.of(encryptedAwsCredentials))

        val credentials = credentialsService.findById("aws").get()
        assertThat(credentials).isEqualTo(plainAWSCredentials)
    }

    @Test
    fun saveAWSCredentials_shouldReturnEncryptCredentials() {
        val plainAWSCredentials = AWSCredentials("access", "secret")
        val encryptedAwsCredentials = AWSCredentials("encryptedAccess", "encryptedSecret")

        `when`(encryptionService.encrypt("access")).thenReturn("encryptedAccess")
        `when`(encryptionService.encrypt("secret")).thenReturn("encryptedSecret")

        val credentials = credentialsService.save(plainAWSCredentials)
        assertThat(credentials).isEqualTo(encryptedAwsCredentials)
    }

    @Test
    fun findAzureRMCredentials_shouldReturnDecryptedCredentials() {
        val encryptedAzureRMCredentials = AzureRMCredentials("encryptedClientId", "encryptedSecret", "encryptedSubscriptionId", "encryptedTenantId", "encryptedEnvironment", "encryptedBackendAccessKey")
        val plainAzureRMCredentials = AzureRMCredentials("clientId", "secret", "subscriptionId", "tenantId", "environment", "backendAccessKey")

        `when`(encryptionService.decrypt("encryptedClientId")).thenReturn("clientId")
        `when`(encryptionService.decrypt("encryptedSecret")).thenReturn("secret")
        `when`(encryptionService.decrypt("encryptedSubscriptionId")).thenReturn("subscriptionId")
        `when`(encryptionService.decrypt("encryptedTenantId")).thenReturn("tenantId")
        `when`(encryptionService.decrypt("encryptedBackendAccessKey")).thenReturn("backendAccessKey")
        `when`(encryptionService.decrypt("encryptedEnvironment")).thenReturn("environment")
        `when`(credentialsRepository.findById("AzureRM")).thenReturn(Optional.of(encryptedAzureRMCredentials))

        val credentials = credentialsService.findById("AzureRM").get()
        assertThat(credentials).isEqualTo(plainAzureRMCredentials)
    }

    @Test
    fun saveAzureRMCredentials_shouldReturnEncryptCredentials() {
        val plainAzureRMCredentials = AzureRMCredentials("clientId", "secret", "subscriptionId", "tenantId", "environment", "backendAccessKey")
        val encryptedAzureRMCredentials = AzureRMCredentials("encryptedClientId", "encryptedSecret", "encryptedSubscriptionId", "encryptedTenantId", "encryptedEnvironment", "encryptedBackendAccessKey")

        `when`(encryptionService.encrypt("clientId")).thenReturn("encryptedClientId")
        `when`(encryptionService.encrypt("secret")).thenReturn("encryptedSecret")
        `when`(encryptionService.encrypt("subscriptionId")).thenReturn("encryptedSubscriptionId")
        `when`(encryptionService.encrypt("tenantId")).thenReturn("encryptedTenantId")
        `when`(encryptionService.encrypt("backendAccessKey")).thenReturn("encryptedBackendAccessKey")
        `when`(encryptionService.encrypt("environment")).thenReturn("encryptedEnvironment")

        val credentials = credentialsService.save(plainAzureRMCredentials)
        assertThat(credentials).isEqualTo(encryptedAzureRMCredentials)
    }

    @Test
    fun findGoogleCredentials_shouldReturnDecryptedCredentials() {
        val encryptedGoogleCredentials = GoogleCredentials("encryptedJSON")
        val plainGoogleCredentials = GoogleCredentials("plainJSON")

        `when`(encryptionService.decrypt("encryptedJSON")).thenReturn("plainJSON")
        `when`(credentialsRepository.findById("Google")).thenReturn(Optional.of(encryptedGoogleCredentials))

        val credentials = credentialsService.findById("Google").get()
        assertThat(credentials).isEqualTo(plainGoogleCredentials)
    }

    @Test
    fun saveGoogleCredentials_shouldReturnEncryptCredentials() {
        val plainGoogleCredentials = GoogleCredentials("plainJSON")
        val encryptedGoogleCredentials = GoogleCredentials("encryptedJSON")

        `when`(encryptionService.encrypt("plainJSON")).thenReturn("encryptedJSON")

        val credentials = credentialsService.save(plainGoogleCredentials)
        assertThat(credentials).isEqualTo(encryptedGoogleCredentials)
    }

}
