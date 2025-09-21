package mk.ukim.finki.soa.masterthesis.repository

import mk.ukim.finki.soa.masterthesis.model.entity.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class UserRepositoryTest @Autowired constructor(
    val userRepository: UserRepository
) {

    @Test
    fun `should find user by username`() {
        val user = User(username = "testuser", email = "test@user.com", password = "test")
        userRepository.save(user)
        val found = userRepository.findByUsername("testuser")
        assertThat(found).isPresent
        assertThat(found.get().username).isEqualTo("testuser")
    }

    @Test
    fun `should find user by email`() {
        val user = User(username = "emailuser", email = "byemail@user.com", password = "test")
        userRepository.save(user)
        val found = userRepository.findByEmail("byemail@user.com")
        assertThat(found).isPresent
        assertThat(found.get().username).isEqualTo("emailuser")
    }
}
