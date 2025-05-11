package edu.eci.cvds.auth.service;

import edu.eci.cvds.auth.dto.AuthRequestDTO;
import edu.eci.cvds.auth.dto.AuthResponseDTO;
import edu.eci.cvds.auth.dto.TokenRefreshRequestDTO;

public interface AuthService {
    
    /**
     * Authenticate a user and generate a JWT token.
     * 
     * @param authRequest the authentication request
     * @return the authentication response with tokens
     */
    AuthResponseDTO authenticate(AuthRequestDTO authRequest);
    
    /**
     * Refresh an existing JWT token using a refresh token.
     * 
     * @param refreshRequest the token refresh request
     * @return the authentication response with new tokens
     */
    AuthResponseDTO refreshToken(TokenRefreshRequestDTO refreshRequest);
}
