package io.gaia_app.credentials

import io.gaia_app.encryption.EncryptionService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class CredentialsServiceTest {

    @Mock
    lateinit var credentialsRepository: CredentialsRepository

    @InjectMocks
    lateinit var credentialsService: CredentialsService

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
        fun loadCredentials_shouldReturnDecryptedCredentials() {
            credentialsService.encryptionService = encryptionService

            val encryptedAwsCredentials = AWSCredentials("encryptedAccess", "encryptedSecret")
            val plainAWSCredentials = AWSCredentials("access", "secret")

            `when`(encryptionService.decryptBatch(listOf("encryptedAccess", "encryptedSecret"))).thenReturn(listOf("access", "secret"))
            `when`(credentialsRepository.findById("aws")).thenReturn(Optional.of(encryptedAwsCredentials))

            val credentials = credentialsService.findById("aws")
            assertThat(credentials).isEqualTo(Optional.of(plainAWSCredentials))
        }

        @Test
        fun saveCredentials_shouldReturnEncryptCredentials() {
            credentialsService.encryptionService = encryptionService

            val plainAWSCredentials = AWSCredentials("access", "secret")
            val encryptedAwsCredentials = AWSCredentials("encryptedAccess", "encryptedSecret")

            `when`(encryptionService.encryptBatch(listOf("access", "secret"))).thenReturn(listOf("encryptedAccess", "encryptedSecret"))

            val credentials = credentialsService.save(plainAWSCredentials)
            assertThat(credentials).isEqualTo(encryptedAwsCredentials)
        }
    }

}
