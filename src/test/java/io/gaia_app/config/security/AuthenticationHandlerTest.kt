package io.gaia_app.config.security

import com.jayway.jsonpath.JsonPath
import io.gaia_app.organizations.User
import io.gaia_app.organizations.repository.UserRepository
import io.gaia_app.test.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor.forClass
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import java.io.PrintWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ExtendWith(MockitoExtension::class)
class AuthenticationHandlerTest {

    @Mock
    lateinit var request: HttpServletRequest

    @Mock
    lateinit var response: HttpServletResponse

    @Nested
    inner class SuccessHandlerTest {

        @Mock
        lateinit var authentication: Authentication

        @Mock
        lateinit var userRepository: UserRepository

        @InjectMocks
        lateinit var successHandler: SuccessHandler

        @BeforeEach
        fun setup() {
            whenever(authentication.name).thenReturn("spencer")
        }

        @Test
        fun `onAuthenticationSuccess() should create user when it does not exist`() {
            // when
            whenever(userRepository.existsById(anyString())).thenReturn(false)
            successHandler.onAuthenticationSuccess(request, response, authentication)

            // then
            val captor = forClass(User::class.java)
            verify(userRepository).save(captor.capture())
            assertThat(captor.value)
                .isNotNull
                .hasFieldOrPropertyWithValue("username", "spencer")
                .hasFieldOrPropertyWithValue("organization", null)
        }

        @Test
        fun `onAuthenticationSuccess() should not create user when it exists`() {
            // when
            whenever(userRepository.existsById(anyString())).thenReturn(true)
            successHandler.onAuthenticationSuccess(request, response, authentication)

            // then
            verify(userRepository, never()).save(any(User::class.java))
        }

        @Test
        fun `onAuthenticationSuccess() should return 200 HTTP`() {
            // when
            whenever(userRepository.existsById(anyString())).thenReturn(true)
            successHandler.onAuthenticationSuccess(request, response, authentication)

            // then
            verify(response).status = HttpServletResponse.SC_OK
            verify(response).contentType = MediaType.APPLICATION_JSON_VALUE
        }

    }

    @Nested
    inner class FailureHandlerTest {

        @Mock
        lateinit var exception: AuthenticationException

        @Mock
        lateinit var writer: PrintWriter

        @InjectMocks
        lateinit var failureHandler: FailureHandler

        @BeforeEach
        fun setup() {
            whenever(response.writer).thenReturn(writer)
        }

        @Test
        fun `onAuthenticationFailure() should return response with data`() {
            // when
            whenever(exception.message).thenReturn("this is so bad...")
            failureHandler.onAuthenticationFailure(request, response, exception)

            // then
            val captor = forClass(String::class.java)
            verify(writer).println(captor.capture())
            assertThat(captor.value).isNotNull()
            val jsonPath = JsonPath.parse(captor.value)
            assertNotNull(jsonPath.read("$.timestamp"))
            assertEquals(HttpServletResponse.SC_UNAUTHORIZED, jsonPath.read("$.status"))
            assertEquals("this is so bad...", jsonPath.read("$.error"))
            assertEquals("Authentication Failed", jsonPath.read("$.message"))
        }

        @Test
        fun `onAuthenticationFailure() should return 401 HTTP`() {
            // when
            failureHandler.onAuthenticationFailure(request, response, exception)

            // then
            verify(response).status = HttpServletResponse.SC_UNAUTHORIZED
            verify(response).contentType = MediaType.APPLICATION_JSON_VALUE
        }

    }

    @Nested
    inner class LogoutHandlerTest {

        @Mock
        lateinit var authentication: Authentication

        @InjectMocks
        lateinit var logoutHandler: LogoutSuccessHandler

        @Test
        fun `onLogoutSuccess() should return 200 HTTP`() {
            // when
            logoutHandler.onLogoutSuccess(request, response, authentication)

            // then
            verify(response).status = HttpServletResponse.SC_OK
            verify(response).contentType = MediaType.APPLICATION_JSON_VALUE
        }

    }

    @Nested
    inner class AuthenticationEntryPointTest {

        @Mock
        lateinit var exception: AuthenticationException

        @Mock
        lateinit var writer: PrintWriter

        @InjectMocks
        lateinit var authenticationEntryPoint: AuthenticationEntryPoint

        @BeforeEach
        fun setup() {
            whenever(response.writer).thenReturn(writer)
        }

        @Test
        fun `commence() should return response with data`() {
            // when
            whenever(exception.message).thenReturn("this is so bad...")
            authenticationEntryPoint.commence(request, response, exception)

            // then
            val captor = forClass(String::class.java)
            verify(writer).println(captor.capture())
            assertThat(captor.value).isNotNull()
            val jsonPath = JsonPath.parse(captor.value)
            assertNotNull(jsonPath.read("$.timestamp"))
            assertEquals(HttpServletResponse.SC_UNAUTHORIZED, jsonPath.read("$.status"))
            assertEquals("this is so bad...", jsonPath.read("$.error"))
            assertEquals("Authentication Failed", jsonPath.read("$.message"))
        }

        @Test
        fun `commence() should return 401 HTTP`() {
            // when
            authenticationEntryPoint.commence(request, response, exception)

            // then
            verify(response).status = HttpServletResponse.SC_UNAUTHORIZED
            verify(response).contentType = MediaType.APPLICATION_JSON_VALUE
        }

    }

    @Nested
    inner class AccessDeniedHandlerTest {

        @Mock
        lateinit var exception: AccessDeniedException

        @Mock
        lateinit var writer: PrintWriter

        @InjectMocks
        lateinit var accessDeniedHandler: AccessDeniedHandler

        @BeforeEach
        fun setup() {
            whenever(response.writer).thenReturn(writer)
        }

        @Test
        fun `handle() should return response with data`() {
            // when
            whenever(exception.message).thenReturn("this is so bad...")
            accessDeniedHandler.handle(request, response, exception)

            // then
            val captor = forClass(String::class.java)
            verify(writer).println(captor.capture())
            assertThat(captor.value).isNotNull()
            val jsonPath = JsonPath.parse(captor.value)
            assertNotNull(jsonPath.read("$.timestamp"))
            assertEquals(HttpServletResponse.SC_FORBIDDEN, jsonPath.read("$.status"))
            assertEquals("this is so bad...", jsonPath.read("$.error"))
            assertEquals("Access Denied", jsonPath.read("$.message"))
        }

        @Test
        fun `handle() should return 403 HTTP`() {
            // when
            accessDeniedHandler.handle(request, response, exception)

            // then
            verify(response).status = HttpServletResponse.SC_FORBIDDEN
            verify(response).contentType = MediaType.APPLICATION_JSON_VALUE
        }

    }

}
