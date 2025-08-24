package mk.ukim.finki.soa.masterthesis.web

import jakarta.validation.Valid
import mk.ukim.finki.soa.masterthesis.model.valueObject.AppRole
import mk.ukim.finki.soa.masterthesis.service.impl.UserService
import mk.ukim.finki.soa.masterthesis.web.dto.AuthResponse
import mk.ukim.finki.soa.masterthesis.web.dto.LoginRequest
import mk.ukim.finki.soa.masterthesis.web.dto.RegisterRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userService: UserService
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody req: RegisterRequest): ResponseEntity<String> {
        userService.register(req.email, req.username, req.password, req.role ?: AppRole.GUEST)
        return ResponseEntity.ok("Registration successful")
    }

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): ResponseEntity<AuthResponse> {
        val token = userService.login(req.usernameOrEmail, req.password)
        return ResponseEntity.ok(AuthResponse(token))
    }
}
