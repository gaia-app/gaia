package io.gaia_app.organizations

import io.gaia_app.organizations.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

interface UserService {

    fun findById(username: String): User

    fun existsById(username: String): Boolean

    fun findAll(): List<User>

    fun create(user:User): User

    fun update(user:User): User

    fun changeUserPassword(username: String, newPassword: String): User

    fun deleteUser(username: String)
}

@Service
class UserServiceImpl(val userRepository: UserRepository, val passwordEncoder: PasswordEncoder):UserService{

    override fun findById(username: String): User = userRepository.findById(username).orElseThrow()

    override fun existsById(username: String): Boolean = userRepository.existsById(username)

    override fun findAll(): List<User> = userRepository.findAll()

    override fun create(user: User): User {
        // encode password before saving to database
        user.password = passwordEncoder.encode(user.password)
        return userRepository.save(user)
    }

    override fun update(user: User): User {
        // reload original user
        val originalUser = userRepository.findById(user.username).orElseThrow()

        // copying password & oauth data to keep them
        user.password = originalUser.password
        user.oAuth2User = originalUser.oAuth2User

        return userRepository.save(user)
    }

    override fun changeUserPassword(username: String, newPassword: String): User {
        val user = userRepository.findById(username).orElseThrow()
        user.password = passwordEncoder.encode(newPassword)

        return userRepository.save(user)
    }

    override fun deleteUser(username: String) {
        userRepository.deleteById(username)
    }
}
