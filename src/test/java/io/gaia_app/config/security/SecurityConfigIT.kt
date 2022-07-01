package io.gaia_app.config.security

import io.gaia_app.organizations.User
import io.gaia_app.organizations.repository.UserRepository
import io.gaia_app.test.any
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.access.annotation.Secured
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@SpringBootTest(classes = [SecurityConfig::class, SecurityConfigIT.FakeRestController::class])
@AutoConfigureMockMvc
@TestPropertySource(properties = ["gaia.admin-password=admin456"])
class SecurityConfigIT {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var successHandler: SuccessHandler

    @MockBean
    lateinit var failureHandler: FailureHandler

    @MockBean
    lateinit var logoutSuccessHandler: LogoutSuccessHandler

    @MockBean
    lateinit var accessDeniedHandler: AccessDeniedHandler

    @MockBean
    lateinit var authenticationEntryPoint: AuthenticationEntryPoint

    @MockBean
    lateinit var userRepository: UserRepository

    @BeforeEach
    internal fun setUp() {
        val adminUser = User("admin", null)
        adminUser.password = "\$2a\$10\$hr8QjaJ0ync5OQoCtoown.XKplCdhAnyfkWaCf9fto9Cd4470hO/e"
        `when`(userRepository.findById("admin")).thenReturn(Optional.of(adminUser));
    }

    @Test
    fun `security should not authenticate assets access`() {
        mockMvc.perform(get("/assets/img/gaia.png"))
        verifyNoInteractions(authenticationEntryPoint)
    }

    @Test
    fun `security should not authenticate favicon access`() {
        mockMvc.perform(get("/favicon.ico"))
        verifyNoInteractions(authenticationEntryPoint)
    }

    @Test
    fun `security should not authenticate api access`() {
        mockMvc.perform(get("/api/url"))
        verify(authenticationEntryPoint).commence(any(), any(), any())
    }

    @Test
    fun `security should not authenticate session access`() {
        mockMvc.perform(get("/auth/user"))
        verify(authenticationEntryPoint).commence(any(), any(), any())
    }

    @Test
    fun `security should not authenticated others access`() {
        mockMvc.perform(get("/path/random"))
        verifyNoInteractions(authenticationEntryPoint)
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun `security should control roles`() {
        mockMvc.perform(get("/admin-access"))
        verify(accessDeniedHandler).handle(any(), any(), any())
    }

    @Test
    fun `security should handle login success`() {
        mockMvc.perform(formLogin("/auth/classic").user("admin").password("admin123"))
            .andExpect(authenticated().withUsername("admin"))
        verify(successHandler).onAuthenticationSuccess(any(), any(), any())
    }

    @Test
    fun `security should handle login failure`() {
        mockMvc.perform(formLogin("/auth/classic").user("admin").password("admin000"))
            .andExpect(unauthenticated())
        verify(failureHandler).onAuthenticationFailure(any(), any(), any())
    }

    @Test
    fun `security should handle logout success`() {
        mockMvc.perform(post("/auth/logout").with(csrf()))
            .andExpect(unauthenticated())
        verify(logoutSuccessHandler).onLogoutSuccess(any(), any(), any())
    }

    @RestController
    class FakeRestController {

        @GetMapping("/admin-access")
        @Secured("ROLE_ADMIN")
        fun adminAccess() = "admin_access"

    }

}
