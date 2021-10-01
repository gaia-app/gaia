package io.gaia_app.teams

import io.gaia_app.teams.repository.UserRepository
import io.gaia_app.test.capture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationEvent
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class AdminUserInitializationTest {

    @Mock
    lateinit var userService: UserService

    @Mock
    lateinit var event: ApplicationReadyEvent

    @InjectMocks
    lateinit var adminUserInitialization: AdminUserInitialization

    @Captor
    lateinit var captor: ArgumentCaptor<User>

    @Test
    fun `the admin account should be created if it doesn't exists on startup`() {
        `when`(userService.existsById("admin")).thenReturn(false)

        adminUserInitialization.setDefaultAdminPassword("make_my_day")
        adminUserInitialization.onApplicationEvent(event)

        verify(userService).existsById("admin")
        verify(userService).create(capture(captor))
        verifyNoMoreInteractions(userService)

        val admin = captor.value
        assertThat(admin.username).isEqualTo("admin")
        assertThat(admin.password).isEqualTo("make_my_day")
        assertThat(admin.isAdmin).isTrue
    }

    @Test
    fun `the admin account should not be created if it exists`() {
        `when`(userService.existsById("admin")).thenReturn(true)

        adminUserInitialization.onApplicationEvent(event)

        verify(userService).existsById("admin")
        verifyNoMoreInteractions(userService)
    }
}
