package io.gaia_app.credentials

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
import java.util.*

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CredentialsServiceTest {

    @Mock
    lateinit var credentialsRepository: CredentialsRepository

    @Mock
    lateinit var encryptionService: EncryptionService

    @InjectMocks
    lateinit var credentialsService: CredentialsService

    @BeforeEach
    internal fun setUp() {
        `when`(encryptionService.encrypt(ArgumentMatchers.anyString())).then(Answer { it.arguments[0] as String })
        `when`(encryptionService.decrypt(ArgumentMatchers.anyString())).then(Answer { it.arguments[0] as String })
    }

    @Test
    fun loadCredentials() {
        val awsCredentials = AWSCredentials("access", "secret")
        `when`(credentialsRepository.findById("aws")).thenReturn(Optional.of(awsCredentials))

        val credentials = credentialsService.findById("aws")
        assertThat(credentials).isEqualTo(Optional.of(awsCredentials))
    }

    @Test
    fun saveCredentials() {
        val awsCredentials = AWSCredentials("access", "secret")
        `when`(credentialsRepository.save(awsCredentials)).thenReturn(awsCredentials)

        credentialsService.save(awsCredentials)

        verify(credentialsRepository).save(awsCredentials)
    }

    @Nested
    inner class EncryptedCredentialsServiceTest {

        @Mock
        lateinit var credentialsRepository: CredentialsRepository

        @Mock
        lateinit var encryptionService: EncryptionService

        @InjectMocks
        lateinit var credentialsService: CredentialsService

        @Test
        fun loadAWSCredentials_shouldReturnDecryptedCredentials() {
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
        fun loadAzureRMCredentials_shouldReturnDecryptedCredentials() {
            val encryptedAzureRMCredentials = AzureRMCredentials("encryptedClientId", "encryptedSecret")
            val plainAzureRMCredentials = AzureRMCredentials("clientId", "secret")

            `when`(encryptionService.decrypt("encryptedClientId")).thenReturn("clientId")
            `when`(encryptionService.decrypt("encryptedSecret")).thenReturn("secret")
            `when`(credentialsRepository.findById("AzureRM")).thenReturn(Optional.of(encryptedAzureRMCredentials))

            val credentials = credentialsService.findById("AzureRM").get()
            assertThat(credentials).isEqualTo(plainAzureRMCredentials)
        }

        @Test
        fun saveAzureRMCredentials_shouldReturnEncryptCredentials() {
            val plainAzureRMCredentials = AzureRMCredentials("clientId", "secret")
            val encryptedAzureRMCredentials = AzureRMCredentials("encryptedClientId", "encryptedSecret")

            `when`(encryptionService.encrypt("clientId")).thenReturn("encryptedClientId")
            `when`(encryptionService.encrypt("secret")).thenReturn("encryptedSecret")

            val credentials = credentialsService.save(plainAzureRMCredentials)
            assertThat(credentials).isEqualTo(encryptedAzureRMCredentials)
        }

        @Test
        fun loadGoogleCredentials_shouldReturnDecryptedCredentials() {
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

}
