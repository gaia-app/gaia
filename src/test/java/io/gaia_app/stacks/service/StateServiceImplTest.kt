package io.gaia_app.stacks.service

import io.gaia_app.stacks.bo.TerraformState
import io.gaia_app.stacks.repository.TerraformStateRepository
import io.gaia_app.test.any
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class StateServiceImplTest {

    @Mock
    lateinit var terraformStateRepository: TerraformStateRepository

    @InjectMocks
    lateinit var stateServiceImpl: StateServiceImpl

    @Test
    fun `findById() should call the repository`() {
        stateServiceImpl.findById("12")

        verify(terraformStateRepository).findById("12")
    }

    @Test
    fun `save() should call the repository`() {
        val state = TerraformState()
        `when`(terraformStateRepository.save(state)).thenReturn(state)

        stateServiceImpl.save(state)

        verify(terraformStateRepository).save(state)
    }
}
