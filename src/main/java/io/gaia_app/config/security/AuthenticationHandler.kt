package io.gaia_app.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.gaia_app.organizations.User
import io.gaia_app.organizations.repository.UserRepository
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.stereotype.Component
import java.time.Instant
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

data class ApiErrorResponse(
    val timestamp: Long = Instant.now().toEpochMilli(),
    val status: Int,
    val error: String?,
    val message: String) {

    fun send(response: HttpServletResponse) {
        response.status = this.status
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.println(ObjectMapper().writeValueAsString(this))
        response.writer.flush()
    }

}

@Component
class SuccessHandler(val userRepository: UserRepository) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        if (!userRepository.existsById(authentication.name)) {
            // enter this case especially for people authenticated with ldap
            userRepository.save(User(authentication.name, null))
        }
        response.status = HttpServletResponse.SC_OK
        response.contentType = MediaType.APPLICATION_JSON_VALUE
    }

}

@Component
class FailureHandler : AuthenticationFailureHandler {

    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
        ApiErrorResponse(
                status = HttpServletResponse.SC_UNAUTHORIZED,
                error = exception.message,
                message = "Authentication Failed"
        ).send(response)
    }

}

@Component
class LogoutSuccessHandler : LogoutSuccessHandler {

    override fun onLogoutSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        response.status = HttpServletResponse.SC_OK
        response.contentType = MediaType.APPLICATION_JSON_VALUE
    }

}

@Component
class AuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
        ApiErrorResponse(
                status = HttpServletResponse.SC_UNAUTHORIZED,
                error = exception.message,
                message = "Authentication Failed"
        ).send(response)
    }

}

@Component
class AccessDeniedHandler : AccessDeniedHandler {

    override fun handle(request: HttpServletRequest, response: HttpServletResponse, exception: AccessDeniedException) {
        ApiErrorResponse(
                status = HttpServletResponse.SC_FORBIDDEN,
                error = exception.message,
                message = "Access Denied"
        ).send(response)
    }

}
