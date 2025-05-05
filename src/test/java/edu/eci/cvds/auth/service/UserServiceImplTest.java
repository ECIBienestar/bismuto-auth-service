package edu.eci.cvds.auth.service;

import edu.eci.cvds.auth.dto.UserRegisterDTO;
import edu.eci.cvds.auth.exception.UserAlreadyExistsException;
import edu.eci.cvds.auth.exception.UserNotFoundException;
import edu.eci.cvds.auth.models.User;
import edu.eci.cvds.auth.models.Role;
import edu.eci.cvds.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRegisterDTO userDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userDto = new UserRegisterDTO("password123", "John Doe", "johna@example.com");
    }

    @Test
    public void testRegisterUserUserAlreadyExists() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(new User()));

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () ->
            userService.registerUser(userDto));

        assertEquals("User with email johna@example.com already exists.", exception.getMessage());
    }

    @Test
    public void testRegisterUserSuccess() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");

        User savedUser = new User("1", userDto.getName(), userDto.getEmail(), "12345", Role.STUDENT);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.registerUser(userDto);

        assertNotNull(result);
        assertEquals(userDto.getEmail(), result.getEmail());
        assertEquals(Role.STUDENT, result.getRole());
        assertEquals("12345", result.getPassword());
    }

    @Test
    public void testFindByEmailUserNotFound() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
            userService.findByEmail(userDto.getEmail()));

        assertEquals("User with email johna@example.com not found.", exception.getMessage());
    }

    @Test
    public void testFindByEmailSuccess() {
        User existingUser = new User("1", userDto.getName(), userDto.getEmail(), "password", Role.STUDENT);
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(existingUser));

        Optional<User> result = userService.findByEmail(userDto.getEmail());

        assertTrue(result.isPresent());
        assertEquals(userDto.getEmail(), result.get().getEmail());
    }

    @Test
    public void testGetUserByIdUserNotFound() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
            userService.getUserById("1"));

        assertEquals("User with id 1 not found.", exception.getMessage());
    }

    @Test
    public void testGetUserByIdSuccess() {
        User existingUser = new User("1", "John Doe", "john@example.com", "password", Role.STUDENT);
        when(userRepository.findById("1")).thenReturn(Optional.of(existingUser));

        Optional<User> result = userService.getUserById("1");

        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());
        assertEquals("john@example.com", result.get().getEmail());
    }
}
