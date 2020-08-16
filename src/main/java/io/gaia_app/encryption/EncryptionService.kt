package io.gaia_app.encryption

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Service for encryption/decryption of data
 */
interface EncryptionService {

    fun encrypt(plaintext:String): String

    fun decrypt(cipherText: String): String

}

@Configuration
class NoOpEncryptionServiceConfig {

    @Bean
    @ConditionalOnMissingBean(EncryptionService::class)
    fun noopEncryptionService() = NoOpEncryptionService()

}

class NoOpEncryptionService:EncryptionService{

    override fun encrypt(plaintext: String) = plaintext

    override fun decrypt(cipherText: String) = cipherText

}
