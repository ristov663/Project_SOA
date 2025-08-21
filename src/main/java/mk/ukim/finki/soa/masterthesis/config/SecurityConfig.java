package mk.ukim.finki.soa.masterthesis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Profile("prod")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()));
        return http.build();
    }

    @Profile("!prod")
    @Bean
    public SecurityFilterChain filterChainDev(HttpSecurity http) throws Exception {
        http
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // disable frame options here
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/actuator/**",
                                "/swagger-ui.html",          // legacy path, still used by some versions
                                "/swagger-ui/**",           // all UI assets (JS, CSS, etc.)
                                "/v3/api-docs/**",          // OpenAPI JSON
                                "/swagger-resources/**",    // required by some Swagger setups
                                "/webjars/**",              // static assets
                                "/h2/**"                    // if you want H2 console open
                        ).permitAll()
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable());      // (optional, common for dev/testing)
        return http.build();
    }
}
