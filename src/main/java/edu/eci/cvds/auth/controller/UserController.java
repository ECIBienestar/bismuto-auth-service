package edu.eci.cvds.auth.controller;

import edu.eci.cvds.auth.models.User;
import edu.eci.cvds.auth.service.UserService;
import edu.eci.cvds.auth.dto.UserResponseDTO;
import edu.eci.cvds.auth.dto.UserRegisterDTO;
import edu.eci.cvds.auth.exception.UserAlreadyExistsException;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * REST controller for handling user-related operations such as registration and lookup.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user.
     *
     * @param userDto the user registration data transfer object
     * @return a response with the created user's basic information or an error message
     */
    @Operation(summary = "Register a new user", description = "Registers a new user with the provided information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully registered."),
            @ApiResponse(responseCode = "409", description = "User already registered."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDTO userDto) {
        try {
            User createdUser = userService.registerUser(userDto);
            UserResponseDTO responseDTO = new UserResponseDTO(
                    createdUser.getId(),
                    createdUser.getName(),
                    createdUser.getEmail()
            );
            return ResponseEntity.ok(responseDTO);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(409).body("User already registered.");
        } catch (Exception e) {
            Logger.getLogger(UserController.class.getName()).severe("Error registering user: " + e.getMessage());
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email the user's email
     * @return the user information if found, or 404 Not Found otherwise
     */
    @Operation(summary = "Get user by email", description = "Retrieves the user information based on the provided email address.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        Optional<User> userOptional = userService.findByEmail(email);
        return userOptional
                .map(user -> ResponseEntity.ok(new UserResponseDTO(user.getId(), user.getName(), user.getEmail())))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the user's ID
     * @return the user information if found, or 404 Not Found otherwise
     */
    @Operation(summary = "Get user by ID", description = "Retrieves the user information based on the provided unique user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id) {
        Optional<User> userOptional = userService.getUserById(id);
        return userOptional
                .map(user -> ResponseEntity.ok(new UserResponseDTO(user.getId(), user.getName(), user.getEmail())))
                .orElse(ResponseEntity.notFound().build());
    }
}
