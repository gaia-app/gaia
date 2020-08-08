package io.gaia_app.stacks.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.gaia_app.encryption.EncryptionService
import io.gaia_app.stacks.bo.TerraformState
import io.gaia_app.stacks.repository.TerraformStateRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.stubbing.Answer
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class EncryptedStateServiceImplTest {

    @Mock
    lateinit var terraformStateRepository: TerraformStateRepository

    @Mock
    lateinit var encryptionService: EncryptionService

    @Spy
    var objectMapper = ObjectMapper()

    @InjectMocks
    lateinit var stateServiceImpl: EncryptedStateServiceImpl

    @Test
    fun `findById() should decrypt state`() {
        // given
        val encryptedState = TerraformState();
        encryptedState.value = mapOf("encrypted" to "encryptedMapValue")

        `when`(encryptionService.decrypt("encryptedMapValue"))
            .thenReturn("""{
                                "state": "content",
                                "nestedContent": {
                                    "key": "value",
                                    "integer": 1,
                                    "boolean": true
                                }
                            }""".trimMargin())
        `when`(terraformStateRepository.findById("12")).thenReturn(Optional.of(encryptedState))

        // when
        val decryptedState = stateServiceImpl.findById("12")

        // then
        assertThat(decryptedState).isNotEmpty
        assertThat(decryptedState.get().value).containsEntry("state","content")
        assertThat(decryptedState.get().value["nestedContent"] as Map<String, Any?>).containsEntry("key", "value")
    }

    @Test
    fun `save() should encrypt state`() {
        // given
        val plainState = TerraformState();
        plainState.value = mapOf(
            "state" to "content",
            "nestedContent" to mapOf(
                "key" to "value",
                "integer" to 1,
                "boolean" to true
            )
        )
        val valueAsString = objectMapper.writeValueAsString(plainState.value)

        `when`(encryptionService.encrypt(valueAsString)).thenReturn("encryptedMapValue")

        `when`(terraformStateRepository.save(any(TerraformState::class.java))).thenAnswer(firstArg())

        // when
        val encryptedState = stateServiceImpl.save(plainState)

        // then
        assertThat(encryptedState.value).isEqualTo(mapOf("encrypted" to "encryptedMapValue"))
    }

}

/**
 * Mockito answer which returns the first argument of the mock invocation.
 */
fun firstArg(): Answer<*>? = Answer { it.arguments[0] }
