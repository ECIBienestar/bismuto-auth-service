package edu.eci.cvds.auth.config;

import edu.eci.cvds.auth.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for Spring Security setup.
 * It defines the security filter chain, password encoder, and authentication manager.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Defines the security filter chain.
     * <p>
     * This method configures the HTTP security settings:
     * - Allows unauthenticated access to authentication endpoints and API documentation.
     * - Requires authentication for all other endpoints.
     * - Disables session creation to support stateless authentication via JWT.
     * - Adds the custom JWT authentication filter before the standard username-password filter.
     *
     * @param http      the HttpSecurity to configure
     * @param jwtFilter the custom JWT authentication filter
     * @return the configured SecurityFilterChain
     * @throws Exception in case of any error during configuration
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",         // Public endpoints for login, token refresh, and validation
                                "/api/users/register",  // Public endpoint for user registration
                                "/swagger-ui/**",       // Swagger UI access
                                "/v3/api-docs/**"       // API documentation access
                        ).permitAll()
                        .anyRequest().authenticated() // All other endpoints require authentication
                )
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter
        return http.build();
    }

    /**
     * Exposes the authentication manager bean, which is used to authenticate credentials.
     *
     * @param config the authentication configuration
     * @return the AuthenticationManager bean
     * @throws Exception if the authentication manager cannot be retrieved
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Defines the password encoder bean used to hash passwords.
     * Uses BCrypt as the hashing algorithm.
     *
     * @return the PasswordEncoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
