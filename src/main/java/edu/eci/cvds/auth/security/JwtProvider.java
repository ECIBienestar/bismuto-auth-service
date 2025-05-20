package edu.eci.cvds.auth.security;

import edu.eci.cvds.auth.config.JwtConfig;
import edu.eci.cvds.auth.exception.AuthException;
import edu.eci.cvds.auth.models.Staff;
import edu.eci.cvds.auth.models.User;
import edu.eci.cvds.auth.models.enums.Role;
import edu.eci.cvds.auth.repository.StaffRepository;
import edu.eci.cvds.auth.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {
    
    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    
    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        
        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        
        String userId = userPrincipal.getUsername();
        
        JwtBuilder tokenBuilder = Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()));
        
        // Add specialty to token if user is staff
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getRole() == Role.MEDICAL_STAFF || 
                user.getRole() == Role.TRAINER || 
                user.getRole() == Role.WELLNESS_STAFF) {
                
                Optional<Staff> staffOpt = staffRepository.findById(userId);
                if (staffOpt.isPresent() && staffOpt.get().getSpecialty() != null) {
                    tokenBuilder.claim("specialty", staffOpt.get().getSpecialty().name());
                }
            }
        }

        return tokenBuilder.signWith(getSigningKey(), SignatureAlgorithm.HS512).compact();
    }
    
    public String generateRefreshToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        String userId = userPrincipal.getUsername();
        
        JwtBuilder tokenBuilder = Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getRefreshExpiration()));
        
        // Add specialty to refresh token if user is staff
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getRole() == Role.MEDICAL_STAFF || 
                user.getRole() == Role.TRAINER || 
                user.getRole() == Role.WELLNESS_STAFF) {
                
                Optional<Staff> staffOpt = staffRepository.findById(userId);
                if (staffOpt.isPresent() && staffOpt.get().getSpecialty() != null) {
                    tokenBuilder.claim("specialty", staffOpt.get().getSpecialty().name());
                }
            }
        }
        
        return tokenBuilder.signWith(getSigningKey(), SignatureAlgorithm.HS512).compact();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new AuthException("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new AuthException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new AuthException("JWT token is expired");
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new AuthException("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new AuthException("JWT claims string is empty");
        }
    }
    
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
                
        return claims.getSubject();
    }
    
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        
        String username = claims.getSubject();
        
        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) claims.get("roles");
        
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
        
        UserDetails principal = new org.springframework.security.core.userdetails.User(username, "", authorities);
        
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
