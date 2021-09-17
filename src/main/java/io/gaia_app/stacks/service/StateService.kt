package io.gaia_app.stacks.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.gaia_app.encryption.EncryptionService
import io.gaia_app.stacks.bo.TerraformState
import io.gaia_app.stacks.repository.TerraformStateRepository
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Service
import java.util.*

/**
 * Service that manages state
 */
interface StateService {

    fun findById(id: String): Optional<TerraformState>

    fun save(state: TerraformState): TerraformState
}

/**
 * Pass-through implementation to the repository
 */
@Service
@ConditionalOnMissingBean(EncryptionService::class)
class StateServiceImpl(val terraformStateRepository: TerraformStateRepository): StateService {

    override fun findById(id: String): Optional<TerraformState> = terraformStateRepository.findById(id)

    override fun save(state: TerraformState): TerraformState = terraformStateRepository.save(state)

}

/**
 * Implementation that encrypts / decrypts the content
 */
@Service
@ConditionalOnBean(EncryptionService::class)
class EncryptedStateServiceImpl(
    val terraformStateRepository: TerraformStateRepository,
    val encryptionService: EncryptionService,
    val objectMapper: ObjectMapper): StateService {

    override fun findById(id: String): Optional<TerraformState> {
        val state = this.terraformStateRepository.findById(id)
        return state.map {
            val decrypted = encryptionService.decrypt(it.value["encrypted"] as String)
            it.value = objectMapper.readValue(decrypted)
            it
        }
    }

    override fun save(state: TerraformState): TerraformState {
        val stateString = objectMapper.writeValueAsString(state.value)
        val encrypted = encryptionService.encrypt(stateString)
        state.value = mapOf("encrypted" to encrypted)
        return terraformStateRepository.save(state)
    }

}
