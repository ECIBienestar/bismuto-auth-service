package edu.eci.cvds.auth.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    public void testHandleUserAlreadyExists() {
        String message = "User already exists";
        UserAlreadyExistsException ex = new UserAlreadyExistsException(message);

        ResponseEntity<String> response = handler.handleUserAlreadyExists(ex);

        assertEquals(CONFLICT, response.getStatusCode());
        assertEquals(message, response.getBody());
    }

    @Test
    public void testHandleUserNotFound() {
        String message = "User not found";
        UserNotFoundException ex = new UserNotFoundException(message);

        ResponseEntity<String> response = handler.handleUserNotFound(ex);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertEquals(message, response.getBody());
    }

    @Test
    public void testHandleInvalidCredentials() {
        String message = "Invalid credentials";
        InvalidCredentialsException ex = new InvalidCredentialsException(message);

        ResponseEntity<String> response = handler.handleInvalidCredentials(ex);

        assertEquals(UNAUTHORIZED, response.getStatusCode());
        assertEquals(message, response.getBody());
    }
}
