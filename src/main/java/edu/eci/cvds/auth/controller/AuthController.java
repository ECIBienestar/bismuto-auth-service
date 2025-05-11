package edu.eci.cvds.auth.controller;

import edu.eci.cvds.auth.dto.AuthRequestDTO;
import edu.eci.cvds.auth.dto.AuthResponseDTO;
import edu.eci.cvds.auth.dto.ErrorResponseDTO;
import edu.eci.cvds.auth.dto.TokenRefreshRequestDTO;
import edu.eci.cvds.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and token management")
public class AuthController {
    
    private final AuthService authService;
    
    @Operation(
        summary = "Authenticate user",
        description = "Authenticates a user with username/email and password, returning JWT token"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Authentication successful",
            content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Authentication failed",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
        )
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequest) {
        AuthResponseDTO response = authService.authenticate(authRequest);
        return ResponseEntity.ok(response);
    }
    
    @Operation(
        summary = "Refresh token",
        description = "Gets a new access token using a valid refresh token"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Token refreshed successfully",
            content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Invalid refresh token",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
        )
    })
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refreshToken(@Valid @RequestBody TokenRefreshRequestDTO refreshRequest) {
        AuthResponseDTO response = authService.refreshToken(refreshRequest);
        return ResponseEntity.ok(response);
    }
}
