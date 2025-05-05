package edu.eci.cvds.auth.service;

import edu.eci.cvds.auth.dto.UserRegisterDTO;
import edu.eci.cvds.auth.exception.UserAlreadyExistsException;
import edu.eci.cvds.auth.exception.UserNotFoundException;
import edu.eci.cvds.auth.models.User;

import java.util.Optional;

/**
 * Service interface for managing user operations.
 * <p>
 * This interface provides methods for user registration, finding users by email or ID, and handling exceptions
 * related to user creation or retrieval.
 */
public interface UserService {

    /**
     * Registers a new user in the system.
     * <p>
     * This method checks if the user already exists, and if not, it creates a new user using the provided data.
     *
     * @param userDto the user data transfer object containing the user's details
     * @return the created {@link User} object
     * @throws UserAlreadyExistsException if a user with the same email already exists
     */
    User registerUser(UserRegisterDTO userDto) throws UserAlreadyExistsException;

    /**
     * Finds a user by their email address.
     * <p>
     * This method retrieves a user by their email. If the user is not found, it throws a {@link UserNotFoundException}.
     *
     * @param email the email address of the user
     * @return an {@link Optional} containing the {@link User} if found, or empty if no user exists with the provided email
     * @throws UserNotFoundException if no user with the given email exists
     */
    Optional<User> findByEmail(String email) throws UserNotFoundException;

    /**
     * Retrieves a user by their ID.
     * <p>
     * This method returns the user associated with the provided ID. If the user does not exist, it throws a {@link UserNotFoundException}.
     *
     * @param id the unique identifier of the user
     * @return an {@link Optional} containing the {@link User} if found, or empty if no user exists with the provided ID
     * @throws UserNotFoundException if no user with the given ID exists
     */
    Optional<User> getUserById(String id) throws UserNotFoundException;
}
