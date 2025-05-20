package edu.eci.cvds.auth.security;

import edu.eci.cvds.auth.config.JwtConfig;
import edu.eci.cvds.auth.exception.AuthException;
import edu.eci.cvds.auth.models.enums.Specialty;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utility class for validating JWT tokens and extracting information from them.
 * 
 * @author Team Bismuto
 * @version 1.0
 * @since 2025-05-18
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenValidator {
    
    private final JwtConfig jwtConfig;
    
    /**
     * Validates if a JWT token is valid and not expired.
     * 
     * @param token the JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("JWT token is expired: {}", e.getMessage());
            return false;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Validates a JWT token and throws AuthException if invalid.
     * 
     * @param token the JWT token to validate
     * @throws AuthException if the token is invalid
     */
    public void validateTokenWithException(String token) throws AuthException {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            log.warn("JWT token is expired: {}", e.getMessage());
            throw new AuthException("JWT token is expired");
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new AuthException("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new AuthException("Invalid JWT token");
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new AuthException("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new AuthException("JWT claims string is empty");
        }
    }
    
    /**
     * Checks if a JWT token is expired.
     * 
     * @param token the JWT token to check
     * @return true if the token is expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            log.error("Error checking token expiration: {}", e.getMessage());
            return true;
        }
    }
    
    /**
     * Gets the expiration date of a JWT token.
     * 
     * @param token the JWT token
     * @return the expiration date
     * @throws AuthException if the token is invalid
     */
    public Date getExpirationDate(String token) throws AuthException {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getExpiration();
        } catch (Exception e) {
            log.error("Error extracting expiration date: {}", e.getMessage());
            throw new AuthException("Error extracting token expiration date");
        }
    }
    
    /**
     * Gets the issued date of a JWT token.
     * 
     * @param token the JWT token
     * @return the issued date
     * @throws AuthException if the token is invalid
     */
    public Date getIssuedDate(String token) throws AuthException {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getIssuedAt();
        } catch (Exception e) {
            log.error("Error extracting issued date: {}", e.getMessage());
            throw new AuthException("Error extracting token issued date");
        }
    }
    
    /**
     * Gets the remaining time in milliseconds before token expiration.
     * 
     * @param token the JWT token
     * @return remaining time in milliseconds, or 0 if expired
     */
    public long getRemainingTimeMs(String token) {
        try {
            Date expiration = getExpirationDate(token);
            long remaining = expiration.getTime() - System.currentTimeMillis();
            return Math.max(0, remaining);
        } catch (Exception e) {
            log.error("Error calculating remaining time: {}", e.getMessage());
            return 0;
        }
    }
    
    /**
     * Gets the remaining time in seconds before token expiration.
     * 
     * @param token the JWT token
     * @return remaining time in seconds, or 0 if expired
     */
    public long getRemainingTimeSeconds(String token) {
        return getRemainingTimeMs(token) / 1000;
    }
    
    /**
     * Gets the username (subject) from the JWT token.
     * 
     * @param token the JWT token
     * @return the username
     * @throws AuthException if the token is invalid
     */
    public String getUsername(String token) throws AuthException {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getSubject();
        } catch (Exception e) {
            log.error("Error extracting username: {}", e.getMessage());
            throw new AuthException("Error extracting username from token");
        }
    }

    /**
     * Gets the specialty from the JWT token if present.
     * 
     * @param token the JWT token
     * @return the specialty or null if not present
     * @throws AuthException if the token is invalid
     */
    public Specialty getSpecialty(String token) throws AuthException {
        try {
            Claims claims = extractAllClaims(token);
            String specialtyStr = claims.get("specialty", String.class);
            return specialtyStr != null ? Specialty.valueOf(specialtyStr) : null;
        } catch (Exception e) {
            log.error("Error extracting specialty: {}", e.getMessage());
            return null;
        }
    }
        
    /**
     * Extracts all claims from a JWT token.
     * 
     * @param token the JWT token
     * @return the claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    /**
     * Gets the signing key for JWT operations.
     * 
     * @return the secret key
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = io.jsonwebtoken.io.Decoders.BASE64.decode(jwtConfig.getSecret());
        return io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes);
    }
    
    /**
     * Creates a comprehensive token validation result with detailed information.
     * 
     * @param token the JWT token to validate
     * @return TokenValidationResult with validation details
     */
    public TokenValidationResult validateTokenDetailed(String token) {
        try {
            Claims claims = extractAllClaims(token);
            boolean isValid = !isTokenExpired(token);
            long remainingMs = getRemainingTimeMs(token);
            String specialtyStr = claims.get("specialty", String.class);
            Specialty specialty = specialtyStr != null ? Specialty.valueOf(specialtyStr) : null;
            
            return TokenValidationResult.builder()
                    .valid(isValid)
                    .expired(isTokenExpired(token))
                    .username(claims.getSubject())
                    .issuedAt(claims.getIssuedAt())
                    .expiresAt(claims.getExpiration())
                    .remainingTimeMs(remainingMs)
                    .humanReadableRemainingTime(getHumanReadableRemainingTime(token))
                    .specialty(specialty)
                    .build();
                    
        } catch (ExpiredJwtException e) {
            return TokenValidationResult.builder()
                    .valid(false)
                    .expired(true)
                    .humanReadableRemainingTime("Expired")
                    .errorMessage("Token is expired")
                    .build();
        } catch (Exception e) {
            return TokenValidationResult.builder()
                    .valid(false)
                    .expired(false)
                    .humanReadableRemainingTime("Unknown")
                    .errorMessage(e.getMessage())
                    .build();
        }
    }
    
    /**
     * Gets human-readable remaining time before token expiration.
     * 
     * @param token the JWT token
     * @return human-readable time string (e.g., "5 hours, 23 minutes, 45 seconds")
     */
    public String getHumanReadableRemainingTime(String token) {
        try {
            long remainingMs = getRemainingTimeMs(token);
            
            if (remainingMs <= 0) {
                return "Expired";
            }
            
            return formatDuration(remainingMs);
        } catch (Exception e) {
            log.error("Error calculating human-readable remaining time: {}", e.getMessage());
            return "Unknown";
        }
    }

    /**
     * Formats milliseconds into a human-readable duration string.
     * 
     * @param milliseconds the duration in milliseconds
     * @return formatted duration string
     */
    private String formatDuration(long milliseconds) {
        long seconds = (milliseconds / 1000) % 60;
        long minutes = (milliseconds / (1000 * 60)) % 60;
        long hours = (milliseconds / (1000 * 60 * 60)) % 24;
        long days = milliseconds / (1000 * 60 * 60 * 24);
        
        StringBuilder sb = new StringBuilder();
        
        appendTimeComponent(sb, days, "day");
        appendTimeComponent(sb, hours, "hour");
        appendTimeComponent(sb, minutes, "minute");
        
        // Always include seconds if no other units or if seconds > 0
        if (seconds > 0 || sb.length() == 0) {
            appendTimeComponent(sb, seconds, "second");
        }
        
        return sb.toString();
    }
    
    /**
     * Helper method to append a time component to the string builder
     */
    private void appendTimeComponent(StringBuilder sb, long value, String unit) {
        if (value <= 0) {
            return;
        }
        
        if (sb.length() > 0) {
            sb.append(", ");
        }
        
        sb.append(value).append(" ").append(unit);
        if (value != 1) {
            sb.append("s");
        }
    }


    /**
     * Result object for detailed token validation.
     */
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class TokenValidationResult {
        private boolean valid;
        private boolean expired;
        private String username;
        private Date issuedAt;
        private Date expiresAt;
        private long remainingTimeMs;
        private String humanReadableRemainingTime;
        private String errorMessage;
        private Specialty specialty;
        
        public long getRemainingTimeSeconds() {
            return remainingTimeMs / 1000;
        }
        
        public boolean isExpiringSoon(long thresholdMinutes) {
            long thresholdMs = thresholdMinutes * 60 * 1000;
            return remainingTimeMs <= thresholdMs;
        }
    }
}