package edu.eci.cvds.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for token validation requests.
 * Contains the token to validate.
 * 
 * @author Jesús Pinzón (Team Bismuto)
 * @version 1.0
 * @since 2025-05-18    
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenValidationRequestDTO {
    
    @NotBlank(message = "Token cannot be blank")
    @Schema(
        description = "JWT token to validate (with or without 'Bearer ' prefix)",
        example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3ODkiLCJyb2xlcyI6WyJTVFVERU5UIl0sImlhdCI6MTc0NzYxNjkyOSwiZXhwIjoxNzQ3NjE4NzI5fQ.lMfClGPdgOCCNQQnZmCSCaOXOMfMt0HA_nOx9OekVvOhm0iRxc7f6dR9OZlx-Q4b732s_po4XnX1qXBYOSt2Sw",
        required = true
    )
    private String token;
}
