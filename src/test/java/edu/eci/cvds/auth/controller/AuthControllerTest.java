package edu.eci.cvds.auth.controller;

import edu.eci.cvds.auth.dto.AuthRequestDTO;
import edu.eci.cvds.auth.dto.AuthResponseDTO;
import edu.eci.cvds.auth.dto.TokenRefreshRequestDTO;
import edu.eci.cvds.auth.models.enums.Role;
import edu.eci.cvds.auth.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private AuthRequestDTO authRequest;
    private TokenRefreshRequestDTO refreshRequest;
    private AuthResponseDTO authResponse;

    @BeforeEach
    void setUp() {
        authRequest = new AuthRequestDTO("user@example.com", "password");
        refreshRequest = new TokenRefreshRequestDTO("refresh-token");

        authResponse = AuthResponseDTO.builder()
                .token("access-token")
                .refreshToken("refresh-token")
                .type("Bearer")
                .id("123")
                .fullName("Test User")
                .email("user@example.com")
                .role(Role.ADMINISTRATOR)
                .build();
    }

    @Test
    void login_Success() {
        when(authService.authenticate(authRequest)).thenReturn(authResponse);

        ResponseEntity<AuthResponseDTO> response = authController.login(authRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(authResponse, response.getBody());
        verify(authService, times(1)).authenticate(authRequest);
    }

    @Test
    void refreshToken_Success() {
        when(authService.refreshToken(refreshRequest)).thenReturn(authResponse);

        ResponseEntity<AuthResponseDTO> response = authController.refreshToken(refreshRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(authResponse, response.getBody());
        verify(authService, times(1)).refreshToken(refreshRequest);
    }
}