package edu.eci.cvds.auth.exception;

/**
 * Exception thrown when a user is not found in the system.
 * <p>
 * This exception is typically used when attempting to retrieve a user
 * based on a non-existent identifier (such as email or ID).
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with a specific message.
     *
     * @param message a description of the error
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
