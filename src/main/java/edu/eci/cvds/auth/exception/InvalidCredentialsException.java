package edu.eci.cvds.auth.exception;

/**
 * Exception thrown when user authentication fails due to invalid credentials.
 * <p>
 * Typically used in login processes when the provided email or password is incorrect.
 */
public class InvalidCredentialsException extends RuntimeException {

    /**
     * Constructs a new InvalidCredentialsException with a specific message.
     *
     * @param message a description of the error
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
