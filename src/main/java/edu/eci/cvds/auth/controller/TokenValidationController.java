package edu.eci.cvds.auth.controller;

import edu.eci.cvds.auth.dto.TokenValidationRequestDTO;
import edu.eci.cvds.auth.dto.TokenValidationResponseDTO;
import edu.eci.cvds.auth.dto.SimpleTokenCheckResponseDTO;
import edu.eci.cvds.auth.dto.ErrorResponseDTO;
import edu.eci.cvds.auth.security.JwtTokenValidator;
import edu.eci.cvds.auth.security.JwtTokenValidator.TokenValidationResult;
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

import java.time.ZoneId;

/**
 * Controller for JWT token validation and introspection operations.
 * 
 * @author Team Bismuto
 * @version 1.0
 * @since 2025-05-18
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Token Validation", description = "Endpoints for JWT token validation and introspection")
public class TokenValidationController {
    
    private final JwtTokenValidator jwtTokenValidator;
    
    @Operation(
        summary = "Validate JWT token with detailed information",
        description = "Validates a JWT token and returns comprehensive details including expiration time, remaining duration, and user information"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Token validation completed (check 'valid' field for actual result)",
            content = @Content(schema = @Schema(implementation = TokenValidationResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request format",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Token is invalid or expired",
            content = @Content(schema = @Schema(implementation = TokenValidationResponseDTO.class))
        )
    })
    @PostMapping("/validate-token")
    public ResponseEntity<TokenValidationResponseDTO> validateToken(@Valid @RequestBody TokenValidationRequestDTO request) {
        String token = request.getToken();
        
        // Remove 'Bearer ' prefix if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        TokenValidationResult result = jwtTokenValidator.validateTokenDetailed(token);
        
        TokenValidationResponseDTO response = TokenValidationResponseDTO.builder()
                .valid(result.isValid())
                .expired(result.isExpired())
                .username(result.getUsername())
                .issuedAt(result.getIssuedAt() != null ? 
                    result.getIssuedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null)
                .expiresAt(result.getExpiresAt() != null ? 
                    result.getExpiresAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null)
                .remainingTime(result.getHumanReadableRemainingTime())
                .errorMessage(result.getErrorMessage())
                .expiringSoon(result.isExpiringSoon(5))
                .build();
        
        if (result.isValid()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }
    
    @Operation(
        summary = "Simple token validity check",
        description = "Performs a basic validation check on a JWT token, returning only validity status and message"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Token check completed",
            content = @Content(schema = @Schema(implementation = SimpleTokenCheckResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request format",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
        )
    })
    @PostMapping("/check-token")
    public ResponseEntity<SimpleTokenCheckResponseDTO> checkToken(@Valid @RequestBody TokenValidationRequestDTO request) {
        String token = request.getToken();
        
        // Remove 'Bearer ' prefix if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        boolean isValid = jwtTokenValidator.isTokenValid(token);
        
        SimpleTokenCheckResponseDTO response = SimpleTokenCheckResponseDTO.builder()
                .valid(isValid)
                .message(isValid ? "Token is valid" : "Token is invalid or expired")
                .build();
        
        return ResponseEntity.ok(response);
    }
}
