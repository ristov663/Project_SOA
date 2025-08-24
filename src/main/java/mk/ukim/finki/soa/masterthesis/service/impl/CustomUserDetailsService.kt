package mk.ukim.finki.soa.masterthesis.service.impl

import mk.ukim.finki.soa.masterthesis.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("User $username not found") }
    }
}
