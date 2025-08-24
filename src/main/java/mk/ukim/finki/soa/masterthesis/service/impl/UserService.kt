package mk.ukim.finki.soa.masterthesis.service.impl

import mk.ukim.finki.soa.masterthesis.config.jwt.JwtUtil
import mk.ukim.finki.soa.masterthesis.model.entity.User
import mk.ukim.finki.soa.masterthesis.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil
) {
    private val passwordEncoder = BCryptPasswordEncoder()

    fun register(email: String, username: String, password: String): String {
        if (userRepository.findByEmail(email).isPresent) throw IllegalArgumentException("Email taken")
        if (userRepository.findByUsername(username).isPresent) throw IllegalArgumentException("Username taken")

        val hashed = passwordEncoder.encode(password)
        val user = userRepository.save(User(username = username, email = email, password = hashed))
        return jwtUtil.generateToken(user)
    }

    fun login(usernameOrEmail: String, password: String): String {
        val user = userRepository.findByUsername(usernameOrEmail)
            .orElseGet { userRepository.findByEmail(usernameOrEmail).orElseThrow { IllegalArgumentException("User not found") } }

        if (!passwordEncoder.matches(password, user.password)) throw IllegalArgumentException("Invalid credentials")
        return jwtUtil.generateToken(user)
    }
}
