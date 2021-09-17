package io.gaia_app.vault

import io.gaia_app.encryption.EncryptionService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import org.springframework.vault.core.VaultTemplate

@Service
@ConditionalOnProperty(name = ["gaia.vault.enabled"], havingValue = "true")
class VaultEncryptionService(val vaultTemplate: VaultTemplate): EncryptionService {

    @Value("\${gaia.vault.encryption.transit.path}")
    lateinit var transitPath: String

    @Value("\${gaia.vault.encryption.transit.key}")
    lateinit var transitKey: String

    override fun encrypt(plaintext: String): String = vaultTemplate.opsForTransit(transitPath).encrypt(transitKey, plaintext)

    override fun decrypt(cipherText: String): String = vaultTemplate.opsForTransit(transitPath).decrypt(transitKey, cipherText)

}
