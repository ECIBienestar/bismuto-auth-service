package edu.eci.cvds.auth.service;

import edu.eci.cvds.auth.dto.UserRegisterDTO;
import edu.eci.cvds.auth.exception.UserAlreadyExistsException;
import edu.eci.cvds.auth.exception.UserNotFoundException;
import edu.eci.cvds.auth.models.User;
import edu.eci.cvds.auth.models.Role;
import edu.eci.cvds.auth.repository.UserRepository;
import edu.eci.cvds.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation for managing user-related operations.
 * <p>
 * This class provides the logic for registering users, finding users by email or ID, and encoding user passwords.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for UserServiceImpl.
     *
     * @param userRepository  the repository for accessing user data in the database
     * @param passwordEncoder the encoder for password hashing
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user in the system.
     * <p>
     * This method checks if the user already exists. If the user exists, it throws a {@link UserAlreadyExistsException}.
     * Otherwise, it creates a new user, encodes the password, and assigns the default role of {@link Role#STUDENT}.
     *
     * @param userDto the user data transfer object containing user details
     * @return the created {@link User} object
     * @throws UserAlreadyExistsException if a user with the same email already exists
     */
    @Override
    public User registerUser(UserRegisterDTO userDto) throws UserAlreadyExistsException {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + userDto.getEmail() + " already exists.");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.STUDENT);

        return userRepository.save(user);
    }

    /**
     * Finds a user by their email.
     * <p>
     * If the user is found, it returns the {@link User}. If the user is not found, it throws a {@link UserNotFoundException}.
     *
     * @param email the email address of the user to be retrieved
     * @return an {@link Optional} containing the {@link User} if found, or empty if not found
     * @throws UserNotFoundException if no user with the given email exists
     */
    @Override
    public Optional<User> findByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with email " + email + " not found.");
        }
        return user;
    }

    /**
     * Retrieves a user by their ID.
     * <p>
     * If the user is found, it returns the {@link User}. If the user is not found, it throws a {@link UserNotFoundException}.
     *
     * @param id the unique identifier of the user to be retrieved
     * @return an {@link Optional} containing the {@link User} if found, or empty if not found
     * @throws UserNotFoundException if no user with the given ID exists
     */
    @Override
    public Optional<User> getUserById(String id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found.");
        }
        return user;
    }
}
