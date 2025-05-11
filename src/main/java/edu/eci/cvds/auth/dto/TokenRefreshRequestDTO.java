package edu.eci.cvds.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenRefreshRequestDTO {
    
    @NotBlank(message = "Refresh token cannot be blank")
    @Schema(description = "Valid refresh token")
    private String refreshToken;
}
