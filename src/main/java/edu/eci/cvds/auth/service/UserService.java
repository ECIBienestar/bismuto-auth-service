package edu.eci.cvds.auth.service;

import edu.eci.cvds.auth.dto.UserRegisterDTO;
import edu.eci.cvds.auth.exception.UserAlreadyExistsException;
import edu.eci.cvds.auth.exception.UserNotFoundException;
import edu.eci.cvds.auth.models.User;

import java.util.Optional;

public interface UserService {
    User registerUser(UserRegisterDTO userDto) throws UserAlreadyExistsException;
    Optional<User> findByEmail(String email) throws UserNotFoundException;
    Optional<User> getUserById(String id) throws UserNotFoundException;
}