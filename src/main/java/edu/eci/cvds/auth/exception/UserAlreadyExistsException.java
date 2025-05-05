package edu.eci.cvds.auth.exception;

/**
 * Exception thrown when attempting to register a user that already exists.
 * <p>
 * Used to prevent duplicate user creation based on unique fields like email.
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new UserAlreadyExistsException with a specific message.
     *
     * @param message a description of the error
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
