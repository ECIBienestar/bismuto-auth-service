package edu.eci.cvds.auth.controller;

import edu.eci.cvds.auth.dto.AuthenticationRequest;
import edu.eci.cvds.auth.dto.AuthenticationResponse;
import edu.eci.cvds.auth.models.User;
import edu.eci.cvds.auth.service.UserService;
import edu.eci.cvds.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller for handling authentication-related endpoints.
 * Provides functionality for user login, token validation, and token refreshing.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    /**
     * Constructor for dependency injection.
     *
     * @param authenticationManager Spring Security authentication manager
     * @param jwtUtil               Utility class for JWT operations
     * @param userService           Service for accessing user data
     */
    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    /**
     * Authenticates the user and generates a JWT token if credentials are valid.
     *
     * @param request the authentication request with email and password
     * @return a response containing the JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String jwt = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    /**
     * Validates the provided JWT token.
     *
     * @param authHeader the Authorization header containing the Bearer token
     * @return a response indicating whether the token is valid
     */
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing or invalid Authorization header"));
        }

        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        boolean valid = jwtUtil.validateToken(token, username);

        return ResponseEntity.ok(Map.of("valid", valid));
    }

    /**
     * Generates a new JWT token if the current token is valid.
     *
     * @param authHeader the Authorization header with the old JWT token
     * @return a response containing the new JWT token
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestHeader("Authorization") String authHeader) {
        String oldToken = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractUsername(oldToken);

        if (!jwtUtil.validateToken(oldToken, email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String newToken = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthenticationResponse(newToken));
    }
}
