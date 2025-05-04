package java.edu.eci.cvds.auth.service;

import java.edu.eci.cvds.auth.models.User;
import java.edu.eci.cvds.auth.models.Role;
import java.edu.eci.cvds.auth.dto.UserRegisterDTO;
import java.edu.eci.cvds.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.edu.eci.cvds.auth.exception.*;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserRegisterDTO userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + userDto.getEmail() + " already exists.");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        if (user.getRole() == null) {
            user.setRole(Role.STUDENT);
        }

        return userRepository.save(user);
    }


    public Optional<User> findByEmail(String email) {
        try{
            return userRepository.findByEmail(email);
        }catch(UserNotFoundException e){
            throw new UserNotFoundException("User with email " + email + " not found.");
        }
    }

    public Optional<User> getUserById(String id) {
        try{
            return userRepository.findById(id);
        }catch(UserNotFoundException e){
            throw new UserNotFoundException("User with id " + id + " not found.");
        }
    }
}