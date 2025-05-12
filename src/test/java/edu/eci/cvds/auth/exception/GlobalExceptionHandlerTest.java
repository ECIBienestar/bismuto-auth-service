package edu.eci.cvds.auth.exception;

import edu.eci.cvds.auth.dto.ErrorResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.validation.DataBinder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private WebRequest webRequest;

    @Test
    void handleAuthException_ShouldReturnUnauthorizedResponse() {
        String errorMessage = "Authentication failed";
        AuthException ex = new AuthException(errorMessage);
        when(webRequest.getDescription(false)).thenReturn("uri=/api/auth/login");

        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.handleAuthException(ex, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        ErrorResponseDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), body.getStatus());
        assertEquals("Authentication Error", body.getError());
        assertEquals(errorMessage, body.getMessage());
        assertEquals("/api/auth/login", body.getPath());
    }

    @Test
    void handleBadCredentialsException_ShouldReturnUnauthorizedResponse() {
        BadCredentialsException ex = new BadCredentialsException("Bad credentials");
        when(webRequest.getDescription(false)).thenReturn("uri=/api/auth/login");

        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.handleAuthenticationException(ex, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        ErrorResponseDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), body.getStatus());
        assertEquals("Authentication Error", body.getError());
        assertEquals("Invalid username or password", body.getMessage());
        assertEquals("/api/auth/login", body.getPath());
    }

    @Test
    void handleJwtException_ShouldReturnUnauthorizedResponse() {
        String errorMessage = "JWT token expired";
        ExpiredJwtException ex = new ExpiredJwtException(null, null, errorMessage);
        when(webRequest.getDescription(false)).thenReturn("uri=/api/auth/refresh");

        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.handleJwtException(ex, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        ErrorResponseDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), body.getStatus());
        assertEquals("JWT Token Error", body.getError());
        assertEquals(errorMessage, body.getMessage());
        assertEquals("/api/auth/refresh", body.getPath());
    }

    @Test
    void handleMethodArgumentNotValidException_ShouldReturnBadRequestResponse() throws Exception {
        Object target = new Object();
        DataBinder binder = new DataBinder(target);
        BindingResult bindingResult = binder.getBindingResult();
        bindingResult.addError(new FieldError("object", "email", "Email is required"));
        bindingResult.addError(new FieldError("object", "password", "Password must be at least 8 characters"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        when(webRequest.getDescription(false)).thenReturn("uri=/api/auth/login");

        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.handleValidationException(ex, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorResponseDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.getStatus());
        assertEquals("Validation Error", body.getError());
        assertEquals("Email is required, Password must be at least 8 characters", body.getMessage());
        assertEquals("/api/auth/login", body.getPath());
    }

    @Test
    void handleGenericException_ShouldReturnInternalServerErrorResponse() {
        Exception ex = new Exception("Unexpected error");
        when(webRequest.getDescription(false)).thenReturn("uri=/api/auth/login");

        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.handleGenericException(ex, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ErrorResponseDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.getStatus());
        assertEquals("Internal Server Error", body.getError());
        assertEquals("An unexpected error occurred", body.getMessage());
        assertEquals("/api/auth/login", body.getPath());
    }
}