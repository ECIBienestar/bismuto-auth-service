package edu.eci.cvds.auth.service.impl;

import edu.eci.cvds.auth.dto.AuthRequestDTO;
import edu.eci.cvds.auth.dto.AuthResponseDTO;
import edu.eci.cvds.auth.dto.TokenRefreshRequestDTO;
import edu.eci.cvds.auth.exception.AuthException;
import edu.eci.cvds.auth.models.User;
import edu.eci.cvds.auth.repository.UserRepository;
import edu.eci.cvds.auth.security.JwtProvider;
import edu.eci.cvds.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    
    @Override
    @Transactional
    public AuthResponseDTO authenticate(AuthRequestDTO authRequest) {
        try {
            // Determine if username is ID or email
            String username = authRequest.getUsername();
            
            // Find user to get info for the response
            User user;
            Optional<User> userOpt = userRepository.findById(username);
            
            if (userOpt.isEmpty()) {
                userOpt = userRepository.findByEmail(username);
                if (userOpt.isPresent()) {
                    username = userOpt.get().getId(); // Use ID for authentication
                }
            }
            
            if (userOpt.isEmpty()) {
                throw new AuthException("User not found: " + authRequest.getUsername());
            }
            
            user = userOpt.get();
            
            // Check if user is active
            if (!user.isActive()) {
                throw new AuthException("User account is inactive");
            }
            
            // Authenticate
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, authRequest.getPassword())
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Generate tokens
            String jwt = jwtProvider.generateToken(authentication);
            String refreshToken = jwtProvider.generateRefreshToken(authentication);
            
            log.info("User authenticated successfully: {}", username);
            
            return AuthResponseDTO.builder()
                    .token(jwt)
                    .refreshToken(refreshToken)
                    .type("Bearer")
                    .id(user.getId())
                    .fullName(user.getFullName())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();
            
        } catch (BadCredentialsException e) {
            log.warn("Authentication failed for user: {}", authRequest.getUsername());
            throw new AuthException("Invalid username or password");
        }
    }
    
    @Override
    @Transactional
    public AuthResponseDTO refreshToken(TokenRefreshRequestDTO refreshRequest) {
        try {
            String refreshToken = refreshRequest.getRefreshToken();
            
            // Validate refresh token
            if (!jwtProvider.validateToken(refreshToken)) {
                throw new AuthException("Invalid refresh token");
            }
            
            // Get user ID from refresh token
            String userId = jwtProvider.getUsernameFromToken(refreshToken);
            
            // Find user
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new AuthException("User not found"));
            
            // Check if user is active
            if (!user.isActive()) {
                throw new AuthException("User account is inactive");
            }
            
            // Create authentication object
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    new org.springframework.security.core.userdetails.User(
                            userId,
                            "",
                            jwtProvider.getAuthentication(refreshToken).getAuthorities()),
                    null,
                    jwtProvider.getAuthentication(refreshToken).getAuthorities()
            );
            
            // Generate new tokens
            String newToken = jwtProvider.generateToken(authentication);
            String newRefreshToken = jwtProvider.generateRefreshToken(authentication);
            
            log.info("Token refreshed successfully for user: {}", userId);
            
            return AuthResponseDTO.builder()
                    .token(newToken)
                    .refreshToken(newRefreshToken)
                    .type("Bearer")
                    .id(user.getId())
                    .fullName(user.getFullName())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();
            
        } catch (Exception e) {
            log.error("Error refreshing token: {}", e.getMessage());
            throw new AuthException("Failed to refresh token: " + e.getMessage());
        }
    }
}
