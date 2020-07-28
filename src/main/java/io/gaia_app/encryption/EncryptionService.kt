package io.gaia_app.encryption

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Service for encryption/decryption of data
 */
interface EncryptionService {

    fun encrypt(plaintext:String): String

    fun encryptBatch(plaintext: List<String>): List<String>

    fun decrypt(cipherText: String): String

    fun decryptBatch(cipherText: List<String>): List<String>

}

@Configuration
class NoOpEncryptionServiceConfig {

    @Bean
    @ConditionalOnMissingBean(EncryptionService::class)
    fun noopEncryptionService() = NoOpEncryptionService()

}

class NoOpEncryptionService:EncryptionService{

    override fun encrypt(plaintext: String) = plaintext

    override fun encryptBatch(plaintext: List<String>) = plaintext

    override fun decrypt(cipherText: String) = cipherText

    override fun decryptBatch(cipherText: List<String>) = cipherText

}
