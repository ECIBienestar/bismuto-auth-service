package edu.eci.cvds.auth.service.impl;

import edu.eci.cvds.auth.dto.AuthRequestDTO;
import edu.eci.cvds.auth.dto.AuthResponseDTO;
import edu.eci.cvds.auth.dto.TokenRefreshRequestDTO;
import edu.eci.cvds.auth.exception.AuthException;
import edu.eci.cvds.auth.models.User;
import edu.eci.cvds.auth.models.enums.Role;
import edu.eci.cvds.auth.repository.UserRepository;
import edu.eci.cvds.auth.security.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    private final String testUserId = "123456789";
    private final String testEmail = "test@example.com";
    private final String testPassword = "password";
    private final String testToken = "testToken";
    private final String testRefreshToken = "testRefreshToken";
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(testUserId)
                .email(testEmail)
                .fullName("Test User")
                .role(Role.STUDENT)
                .password(testPassword)
                .active(true)
                .build();
    }

    @Test
    void authenticateShouldReturnAuthResponseWhenValidCredentialsById() throws AuthException {
        AuthRequestDTO request = new AuthRequestDTO(testUserId, testPassword);
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                testUserId,
                testPassword,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_STUDENT"))
        );
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtProvider.generateToken(authentication)).thenReturn(testToken);
        when(jwtProvider.generateRefreshToken(authentication)).thenReturn(testRefreshToken);

        AuthResponseDTO response = authService.authenticate(request);

        assertNotNull(response);
        assertEquals(testToken, response.getToken());
        assertEquals(testRefreshToken, response.getRefreshToken());
        assertEquals("Bearer", response.getType());
        assertEquals(testUserId, response.getId());
        assertEquals("Test User", response.getFullName());
        assertEquals(testEmail, response.getEmail());
        assertEquals(Role.STUDENT, response.getRole());

        verify(userRepository).findById(testUserId);
        verify(authenticationManager).authenticate(any());
        verify(jwtProvider).generateToken(authentication);
        verify(jwtProvider).generateRefreshToken(authentication);
    }

    @Test
    void authenticateShouldReturnAuthResponseWhenValidCredentialsByEmail() throws AuthException {
        AuthRequestDTO request = new AuthRequestDTO(testEmail, testPassword);
        when(userRepository.findById(testEmail)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.of(testUser));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                testUserId,
                testPassword,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_STUDENT"))
        );
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtProvider.generateToken(authentication)).thenReturn(testToken);
        when(jwtProvider.generateRefreshToken(authentication)).thenReturn(testRefreshToken);

        AuthResponseDTO response = authService.authenticate(request);

        assertNotNull(response);
        assertEquals(testUserId, response.getId());
        verify(userRepository).findById(testEmail);
        verify(userRepository).findByEmail(testEmail);
    }

    @Test
    void authenticateShouldThrowAuthExceptionWhenUserNotFound() {
        AuthRequestDTO request = new AuthRequestDTO("nonexistent", testPassword);
        when(userRepository.findById("nonexistent")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("nonexistent")).thenReturn(Optional.empty());

        assertThrows(AuthException.class, () -> authService.authenticate(request));
    }

    @Test
    void authenticateShouldThrowAuthExceptionWhenUserInactive() {
        testUser.setActive(false);
        AuthRequestDTO request = new AuthRequestDTO(testUserId, testPassword);
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));

        assertThrows(AuthException.class, () -> authService.authenticate(request));
    }

    @Test
    void authenticateShouldThrowAuthExceptionWhenBadCredentials() {
        AuthRequestDTO request = new AuthRequestDTO(testUserId, "wrongpassword");
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(AuthException.class, () -> authService.authenticate(request));
    }

    @Test
    void refreshTokenShouldThrowAuthExceptionWhenInvalidRefreshToken() {
        TokenRefreshRequestDTO request = new TokenRefreshRequestDTO("invalidToken");
        when(jwtProvider.validateToken("invalidToken")).thenReturn(false);

        assertThrows(AuthException.class, () -> authService.refreshToken(request));
    }

    @Test
    void refreshTokenShouldThrowAuthExceptionWhenUserNotFound() {
        TokenRefreshRequestDTO request = new TokenRefreshRequestDTO(testRefreshToken);
        when(jwtProvider.validateToken(testRefreshToken)).thenReturn(true);
        when(jwtProvider.getUsernameFromToken(testRefreshToken)).thenReturn(testUserId);
        when(userRepository.findById(testUserId)).thenReturn(Optional.empty());

        assertThrows(AuthException.class, () -> authService.refreshToken(request));
    }

    @Test
    void refreshTokenShouldThrowAuthExceptionWhenUserInactive() {
        testUser.setActive(false);
        TokenRefreshRequestDTO request = new TokenRefreshRequestDTO(testRefreshToken);
        when(jwtProvider.validateToken(testRefreshToken)).thenReturn(true);
        when(jwtProvider.getUsernameFromToken(testRefreshToken)).thenReturn(testUserId);
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));

        assertThrows(AuthException.class, () -> authService.refreshToken(request));
    }

    @Test
    void refreshTokenShouldReturnNewAuthResponseWhenValidRefreshToken() throws AuthException {
        TokenRefreshRequestDTO request = new TokenRefreshRequestDTO(testRefreshToken);
        when(jwtProvider.validateToken(testRefreshToken)).thenReturn(true);
        when(jwtProvider.getUsernameFromToken(testRefreshToken)).thenReturn(testUserId);
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_STUDENT");
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(
                        testUserId,
                        "",
                        Collections.singletonList(authority)),
                null,
                Collections.singletonList(authority)
        );
        when(jwtProvider.getAuthentication(testRefreshToken)).thenReturn(authentication);
        when(jwtProvider.generateToken(any(Authentication.class))).thenReturn("newToken");
        when(jwtProvider.generateRefreshToken(any(Authentication.class))).thenReturn("newRefreshToken");

        AuthResponseDTO response = authService.refreshToken(request);

        assertNotNull(response);
        assertEquals("newToken", response.getToken());
        assertEquals("newRefreshToken", response.getRefreshToken());
        assertEquals(testUserId, response.getId());
    }
}