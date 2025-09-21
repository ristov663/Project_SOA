package mk.ukim.finki.soa.masterthesis.service

import mk.ukim.finki.soa.masterthesis.model.entity.User
import mk.ukim.finki.soa.masterthesis.repository.UserRepository
import mk.ukim.finki.soa.masterthesis.service.impl.CustomUserDetailsService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.util.*

class CustomUserDetailsServiceTest {
    private val repo = mock<UserRepository>()
    private val service = CustomUserDetailsService(repo)

    @Test
    fun `loadUserByUsername returns user`() {
        val u = User(1L, "mail@mail", "hash", "password")
        whenever(repo.findByUsername("username")).thenReturn(Optional.of(u))
        assertEquals(u, service.loadUserByUsername("username"))
    }

    @Test
    fun `loadUserByUsername throws if not found`() {
        whenever(repo.findByUsername("notexisting")).thenReturn(Optional.empty())
        assertThrows(UsernameNotFoundException::class.java) { service.loadUserByUsername("notexisting") }
    }
}
