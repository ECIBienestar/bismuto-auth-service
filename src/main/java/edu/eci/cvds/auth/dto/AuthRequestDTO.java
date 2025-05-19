package edu.eci.cvds.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Data Transfer Object for authentication requests.
 * Contains the username and password for user authentication.
 * 
 * @author Jesús Pinzón (Team Bismuto)
 * @version 1.0
 * @since 2025-05-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {
    
    @NotBlank(message = "Username cannot be blank")
    @Schema(description = "User ID or email", example = "1020304050")
    private String username;
    
    @NotBlank(message = "Password cannot be blank")
    @Schema(description = "User password", example = "SecureP@ssword123")
    private String password;
}
