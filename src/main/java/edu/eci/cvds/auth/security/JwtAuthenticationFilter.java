package edu.eci.cvds.auth.security;

import edu.eci.cvds.auth.service.UserService;
import edu.eci.cvds.auth.models.User;
import edu.eci.cvds.auth.util.JwtUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Filter that intercepts incoming HTTP requests to validate the JWT and authenticate the user.
 * <p>
 * This filter extracts the JWT token from the request, validates it, and if the token is valid,
 * it authenticates the user by setting the security context with their details and authorities.
 * The filter is executed once per request, ensuring that every request is properly authenticated.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    /**
     * Constructor that initializes the JWT utility and user service.
     *
     * @param jwtUtil the utility class for working with JWT tokens
     * @param userService the service used to fetch user details from the database
     */
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    /**
     * Filters the request to check if the user has a valid JWT.
     * <p>
     * If the token is valid, it loads the user from the database, extracts their authorities,
     * and sets the authentication context.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain to continue processing the request
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        if (token != null && jwtUtil.validateToken(token, jwtUtil.extractUsername(token))) {
            Optional<User> optionalUser = userService.findByEmail(jwtUtil.extractUsername(token));
            if (optionalUser.isPresent()) {
                var userDetails = optionalUser.get();
                var authorities = List.of(new SimpleGrantedAuthority(userDetails.getRole().name()));
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the HTTP request's Authorization header.
     * <p>
     * The token is expected to be in the format "Bearer <token>".
     *
     * @param request the HTTP request
     * @return the JWT token if present, otherwise null
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
