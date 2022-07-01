package io.gaia_app.config

import io.gaia_app.organizations.User
import io.gaia_app.organizations.repository.UserRepository
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class SpringSecurityUserAuditorAware(val userRepository: UserRepository):AuditorAware<User> {

    override fun getCurrentAuditor(): Optional<User> {
        val authentication = SecurityContextHolder.getContext().authentication ?: return Optional.empty()
        return userRepository.findById(authentication.name)
    }

}

