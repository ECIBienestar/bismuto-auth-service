package edu.eci.cvds.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data Transfer Object for simple token validation responses.
 * Contains a boolean indicating whether the token is valid or not.
 * 
 * @author Jesús Pinzón (Team Bismuto)
 * @version 1.0
 * @since 2025-05-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleTokenCheckResponseDTO {
    
    @Schema(description = "Whether the token is valid", example = "true")
    private boolean valid;
    
    @Schema(description = "Validation message", example = "Token is valid")
    private String message;
}
