package mk.ukim.finki.soa.masterthesis.service

import mk.ukim.finki.soa.masterthesis.config.jwt.JwtUtil
import mk.ukim.finki.soa.masterthesis.model.entity.User
import mk.ukim.finki.soa.masterthesis.model.valueObject.AppRole
import mk.ukim.finki.soa.masterthesis.repository.UserRepository
import mk.ukim.finki.soa.masterthesis.service.impl.UserService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

class UserServiceTest {

    private lateinit var userRepository: UserRepository
    private lateinit var jwtUtil: JwtUtil
    private lateinit var service: UserService

    @BeforeEach
    fun setup() {
        userRepository = mock()
        jwtUtil = mock()
        service = UserService(userRepository, jwtUtil)
    }

    @Test
    fun `register succeeds with unique username and email`() {
        whenever(userRepository.findByEmail(any())).thenReturn(Optional.empty())
        whenever(userRepository.findByUsername(any())).thenReturn(Optional.empty())
        whenever(jwtUtil.generateToken(any<User>())).thenReturn("token")
        whenever(userRepository.save(any<User>())).thenAnswer { it.arguments[0] as User }

        val token = service.register("a@test.com", "testuser", "pw", AppRole.STUDENT)
        assertThat(token).isEqualTo("token")
        verify(userRepository).save(check { assertThat(it.username).isEqualTo("testuser") })
    }

    @Test
    fun `register fails with taken email`() {
        whenever(userRepository.findByEmail("a@test.com")).thenReturn(
            Optional.of(
                User(
                    1L,
                    "a@test.com",
                    "pw",
                    "password"
                )
            )
        )
        assertThatThrownBy { service.register("a@test.com", "user2", "pw", AppRole.STUDENT) }
            .isInstanceOf(IllegalArgumentException::class.java).hasMessage("Email taken")
    }

    @Test
    fun `register fails with taken username`() {
        whenever(userRepository.findByEmail(any())).thenReturn(Optional.empty())
        whenever(userRepository.findByUsername("u")).thenReturn(
            Optional.of(
                User(
                    1L,
                    "other@test.com",
                    "pw",
                    "password"
                )
            )
        )
        assertThatThrownBy { service.register("x@test.com", "u", "pw", AppRole.STUDENT) }
            .isInstanceOf(IllegalArgumentException::class.java).hasMessage("Username taken")
    }

    @Test
    fun `login succeeds for username`() {
        val hashed = BCryptPasswordEncoder().encode("pw")
        val user = User(username = "test", email = "t@test.com", password = hashed, role = AppRole.STUDENT)
        whenever(userRepository.findByUsername("test")).thenReturn(Optional.of(user))
        whenever(jwtUtil.generateToken(user)).thenReturn("token")
        val token = service.login("test", "pw")
        assertThat(token).isEqualTo("token")
    }

    @Test
    fun `login succeeds for email`() {
        val hashed = BCryptPasswordEncoder().encode("pw")
        val user = User(username = "test2", email = "e@test.com", password = hashed, role = AppRole.STUDENT)
        whenever(userRepository.findByUsername("nope")).thenReturn(Optional.empty())
        whenever(userRepository.findByEmail("nope")).thenReturn(Optional.of(user))
        whenever(jwtUtil.generateToken(user)).thenReturn("token2")
        val token = service.login("nope", "pw")
        assertThat(token).isEqualTo("token2")
    }

    @Test
    fun `login fails for user not found`() {
        whenever(userRepository.findByUsername(any())).thenReturn(Optional.empty())
        whenever(userRepository.findByEmail(any())).thenReturn(Optional.empty())
        assertThatThrownBy { service.login("nouser", "pw") }
            .isInstanceOf(IllegalArgumentException::class.java).hasMessage("User not found")
    }

    @Test
    fun `login fails for invalid credentials`() {
        val user = User(
            username = "test",
            email = "mail",
            password = BCryptPasswordEncoder().encode("goodpw"),
            role = AppRole.STUDENT
        )
        whenever(userRepository.findByUsername("test")).thenReturn(Optional.of(user))
        assertThatThrownBy { service.login("test", "badpw") }
            .isInstanceOf(IllegalArgumentException::class.java).hasMessage("Invalid credentials")
    }
}
