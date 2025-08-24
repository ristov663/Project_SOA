package mk.ukim.finki.soa.masterthesis.config.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import mk.ukim.finki.soa.masterthesis.model.entity.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {
    private val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun extractUsername(token: String): String? =
        Jwts.parserBuilder().setSigningKey(secretKey).build()
            .parseClaimsJws(token).body.subject

    fun extractRole(token: String): String? =
        Jwts.parserBuilder().setSigningKey(secretKey).build()
            .parseClaimsJws(token).body["role"] as String?

    fun generateToken(user: User): String {
        val claims: MutableMap<String, Any> = HashMap()
        claims["role"] = user.getRole().roleName

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(secretKey)
            .compact()
    }

    fun generateToken(userDetails: UserDetails, role: String): String {
        val claims: MutableMap<String, Any> = HashMap()
        claims["role"] = role

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(secretKey)
            .compact()
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username && !isTokenExpired(token))
    }

    private fun isTokenExpired(token: String): Boolean =
        Jwts.parserBuilder().setSigningKey(secretKey).build()
            .parseClaimsJws(token).body.expiration.before(Date())
}
