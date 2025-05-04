package java.edu.eci.cvds.auth.controller;

import java.edu.eci.cvds.auth.models.User;
import java.edu.eci.cvds.auth.service.UserService;
import java.edu.eci.cvds.auth.dto.UserResponseDTO;
import java.edu.eci.cvds.auth.dto.UserRegisterDTO;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.edu.eci.cvds.auth.exception.UserAlreadyExistsException;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRegisterDTO userDto) {
        try {
            User createdUser = userService.registerUser(userDto);
            UserResponseDTO responseDTO = new UserResponseDTO(
                    createdUser.getId(),
                    createdUser.getName(),
                    createdUser.getEmail()
            );
            return ResponseEntity.ok(responseDTO);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(409).build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        Optional<User> userOptional = userService.findByEmail(email);
        return userOptional
                .map(user -> ResponseEntity.ok(new UserResponseDTO(user.getId(), user.getName(), user.getEmail())))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id) {
        Optional<User> userOptional = userService.getUserById(id);
        return userOptional
                .map(user -> ResponseEntity.ok(new UserResponseDTO(user.getId(), user.getName(), user.getEmail())))
                .orElse(ResponseEntity.notFound().build());
    }
}