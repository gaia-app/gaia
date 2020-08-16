package io.gaia_app.vault

import io.gaia_app.test.VaultContainerTestInitializer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.vault.core.VaultTemplate
import org.springframework.vault.support.VaultMount
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(
    classes = [ VaultEncryptionService::class, VaultConfiguration::class ],
    properties = [
    "gaia.vault.enabled=true",
    "gaia.vault.encryption.transit.path=/gaia-transit",
    "gaia.vault.encryption.transit.key=gaia-key"
])
@ContextConfiguration(initializers = [VaultContainerTestInitializer::class])
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class VaultEncryptionServiceIT {

    @Autowired
    lateinit var vaultTemplate: VaultTemplate

    @BeforeAll
    fun setUp() {
        // creating transit secret engine
        vaultTemplate.opsForSys().mount("/gaia-transit", VaultMount.builder().type("transit").build())
    }

    @Autowired
    lateinit var vaultEncryptionService: VaultEncryptionService

    @Test
    fun encrypt() {
        val encrypted = vaultEncryptionService.encrypt("some_text")
        assertThat(encrypted).startsWith("vault:v1:")
    }

    @Test
    fun decrypt() {
        val encrypted = vaultEncryptionService.encrypt("some_text")
        val plain = vaultEncryptionService.decrypt(encrypted)
        assertThat(plain).isEqualTo("some_text")
    }
}
