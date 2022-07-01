package io.gaia_app.credentials

import io.gaia_app.organizations.User
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class PlainCredentialsServiceTest {

    @Mock
    lateinit var credentialsRepository: CredentialsRepository

    @InjectMocks
    lateinit var plainCredentialsService: PlainCredentialsService

    @Test
    fun findById() {
        plainCredentialsService.findById("12")

        verify(credentialsRepository).findById("12")
    }

    @Test
    fun findByIdAndCreatedBy() {
        val john = User("john", null)
        plainCredentialsService.findByIdAndCreatedBy("12", john)

        verify(credentialsRepository).findByIdAndCreatedBy("12", john)
    }

    @Test
    fun findAllByCreatedBy() {
        val john = User("john", null)
        plainCredentialsService.findAllByCreatedBy(john)

        verify(credentialsRepository).findAllByCreatedBy(john)
    }

    @Test
    fun save() {
        val credentials = AWSCredentials("access", "seccret")
        `when`(credentialsRepository.save(credentials)).thenReturn(credentials)
        plainCredentialsService.save(credentials)

        verify(credentialsRepository).save(credentials)
    }

    @Test
    fun deleteById() {
        plainCredentialsService.deleteById("12")

        verify(credentialsRepository).deleteById("12")
    }

    @Test
    fun load() {
        plainCredentialsService.load("12")

        verify(credentialsRepository).findById("12")
    }
}
