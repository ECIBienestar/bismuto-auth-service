package edu.eci.cvds.auth.dto;

import edu.eci.cvds.auth.models.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for authentication responses.
 * Contains the authentication token and refresh token.
 * 
 * @author Jesús Pinzón (Team Bismuto)
 * @version 1.0
 * @since 2025-05-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    
    @Schema(description = "Authentication token (JWT)")
    private String token;
    
    @Schema(description = "Refresh token")
    private String refreshToken;
    
    @Schema(description = "Token type", example = "Bearer")
    private String type = "Bearer";
    
    @Schema(description = "User ID")
    private String id;
    
    @Schema(description = "User full name")
    private String fullName;
    
    @Schema(description = "User email")
    private String email;
    
    @Schema(description = "User role")
    private Role role;
}
