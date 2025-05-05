package edu.eci.cvds.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Global exception handler for the authentication module.
 * <p>
 * Catches and handles custom and general exceptions thrown by controllers,
 * and returns appropriate HTTP responses with status codes and messages.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the case where a user is already registered.
     *
     * @param ex the exception thrown
     * @return HTTP 409 CONFLICT with the exception message
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    /**
     * Handles the case where a user is not found in the database.
     *
     * @param ex the exception thrown
     * @return HTTP 404 NOT FOUND with the exception message
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles invalid login attempts with incorrect credentials.
     *
     * @param ex the exception thrown
     * @return HTTP 401 UNAUTHORIZED with the exception message
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    /**
     * Handles any other unexpected exceptions.
     *
     * @param ex the exception thrown
     * @return HTTP 500 INTERNAL SERVER ERROR with a generic message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }
}
