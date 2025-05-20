package edu.eci.cvds.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import edu.eci.cvds.auth.models.enums.Specialty;

/**
 * Data Transfer Object for token validation responses.
 * Contains the validation result and additional information.
 * 
 * @author Jesús Pinzón (Team Bismuto)
 * @version 1.0
 * @since 2025-05-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenValidationResponseDTO {
    
    @Schema(description = "Whether the token is valid", example = "true")
    private boolean valid;
    
    @Schema(description = "Whether the token is expired", example = "false")
    private boolean expired;
    
    @Schema(description = "Username extracted from token", example = "123456789")
    private String username;

    @Schema(description = "User specialty (for staff members)", nullable = true, example = "GENERAL_MEDICINE")
    private Specialty specialty;
    
    @Schema(description = "When the token was issued", example = "2025-05-18T14:28:49")
    private LocalDateTime issuedAt;
    
    @Schema(description = "When the token expires", example = "2025-05-18T14:58:49")
    private LocalDateTime expiresAt;
    
    @Schema(description = "Human-readable remaining time", example = "29 minutes, 30 seconds")
    private String remainingTime;
    
    @Schema(description = "Error message if validation failed", example = "Token is expired")
    private String errorMessage;
    
    @Schema(description = "Whether token expires soon (within 5 minutes)", example = "false")
    private boolean expiringSoon;
}
