package mk.ukim.finki.soa.masterthesis.model.entity

import jakarta.persistence.*
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import mk.ukim.finki.soa.masterthesis.model.valueObject.AppRole

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    private val username: String,

    @Column(unique = true, nullable = false)
    private val email: String,

    @Column(nullable = false)
    private var password: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private val role: AppRole = AppRole.GUEST
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority(role.roleName))

    override fun getPassword(): String = password
    override fun getUsername(): String = username
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

    fun getEmail(): String = email
    fun getRole(): AppRole = role
}
