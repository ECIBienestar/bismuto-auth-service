package edu.eci.cvds.auth.models;

import edu.eci.cvds.auth.models.enums.Role;
import edu.eci.cvds.auth.models.enums.IdType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateValidUser() {
        User user = User.builder()
                .id("123456789")
                .idType(IdType.CC)
                .fullName("John Doe")
                .phone("1234567890")
                .email("john.doe@example.com")
                .role(Role.STUDENT)
                .password("securePassword123")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    void shouldFailWhenIdIsBlank() {
        User user = User.builder()
                .id("")  // Blank ID
                .idType(IdType.CC)
                .fullName("John Doe")
                .phone("1234567890")
                .email("john.doe@example.com")
                .role(Role.STUDENT)
                .password("securePassword123")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Debería fallar por ID en blanco");
        assertEquals(1, violations.stream()
                .filter(v -> v.getMessage().equals("ID cannot be blank"))
                .count());
    }

    @Test
    void shouldFailWhenIdIsTooShort() {
        User user = User.builder()
                .id("1234")
                .idType(IdType.CC)
                .fullName("John Doe")
                .phone("1234567890")
                .email("john.doe@example.com")
                .role(Role.STUDENT)
                .password("securePassword123")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Debería fallar por ID demasiado corto");
        assertEquals(1, violations.stream()
                .filter(v -> v.getMessage().equals("ID must be between 5 and 15 characters"))
                .count());
    }

    @Test
    void shouldFailWhenFullNameIsBlank() {
        User user = User.builder()
                .id("123456789")
                .idType(IdType.CC)
                .fullName("")
                .phone("1234567890")
                .email("john.doe@example.com")
                .role(Role.STUDENT)
                .password("securePassword123")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Debería fallar por nombre en blanco");
        assertEquals(1, violations.stream()
                .filter(v -> v.getMessage().equals("Full name cannot be blank"))
                .count());
    }

    @Test
    void shouldFailWhenEmailIsInvalid() {
        User user = User.builder()
                .id("123456789")
                .idType(IdType.CC)
                .fullName("John Doe")
                .phone("1234567890")
                .email("invalid-email")
                .role(Role.STUDENT)
                .password("securePassword123")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Debería fallar por email inválido");
        assertEquals(1, violations.stream()
                .filter(v -> v.getMessage().equals("Email must be valid"))
                .count());
    }

    @Test
    void shouldFailWhenPasswordIsBlank() {
        User user = User.builder()
                .id("123456789")
                .idType(IdType.CC)
                .fullName("John Doe")
                .phone("1234567890")
                .email("john.doe@example.com")
                .role(Role.STUDENT)
                .password("")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Debería fallar por contraseña en blanco");
        assertEquals(1, violations.stream()
                .filter(v -> v.getMessage().equals("Password cannot be blank"))
                .count());
    }

    @Test
    void shouldChangeActiveStatus() {
        User user = User.builder()
                .id("123456789")
                .idType(IdType.CC)
                .fullName("John Doe")
                .phone("1234567890")
                .email("john.doe@example.com")
                .role(Role.STUDENT)
                .password("securePassword123")
                .build();

        user.setActive(false);
        assertFalse(user.isActive(), "El estado activo debería cambiar a falso");
    }

    @Test
    void shouldSupportAllIdTypes() {
        User user = User.builder()
                .id("123456789")
                .idType(IdType.NUIP)
                .fullName("John Doe")
                .phone("1234567890")
                .email("john.doe@example.com")
                .role(Role.STUDENT)
                .password("securePassword123")
                .build();

        assertEquals(IdType.NUIP, user.getIdType(), "Debería soportar todos los tipos de ID");
    }

    @Test
    void shouldSupportAllRoles() {
        User user = User.builder()
                .id("123456789")
                .idType(IdType.CC)
                .fullName("John Doe")
                .phone("1234567890")
                .email("john.doe@example.com")
                .role(Role.ADMINISTRATOR)
                .password("securePassword123")
                .build();

        assertEquals(Role.ADMINISTRATOR, user.getRole(), "Debería soportar todos los roles");
    }

    @Test
    void shouldHaveActiveTrueByDefault() {
        User user = User.builder()
                .id("123456789")
                .idType(IdType.CC)
                .fullName("John Doe")
                .phone("1234567890")
                .email("john.doe@example.com")
                .role(Role.STUDENT)
                .active(true)
                .password("securePassword123")
                .build();

        assertTrue(user.isActive(), "El usuario debería estar activo por defecto");
    }

    @Test
    void shouldGetAndSetId() {
        User user = new User();
        String testId = "987654321";
        user.setId(testId);
        assertEquals(testId, user.getId(), "Getter/Setter for ID should work");
    }

    @Test
    void shouldGetAndSetIdType() {
        User user = new User();
        IdType testIdType = IdType.TI;
        user.setIdType(testIdType);
        assertEquals(testIdType, user.getIdType(), "Getter/Setter for ID Type should work");
    }

    @Test
    void shouldGetAndSetFullName() {
        User user = new User();
        String testName = "Jane Smith";
        user.setFullName(testName);
        assertEquals(testName, user.getFullName(), "Getter/Setter for Full Name should work");
    }

    @Test
    void shouldGetAndSetPhone() {
        User user = new User();
        String testPhone = "9876543210";
        user.setPhone(testPhone);
        assertEquals(testPhone, user.getPhone(), "Getter/Setter for Phone should work");
    }

    @Test
    void shouldGetAndSetEmail() {
        User user = new User();
        String testEmail = "jane.smith@example.com";
        user.setEmail(testEmail);
        assertEquals(testEmail, user.getEmail(), "Getter/Setter for Email should work");
    }

    @Test
    void shouldGetAndSetRole() {
        User user = new User();
        Role testRole = Role.TRAINER;
        user.setRole(testRole);
        assertEquals(testRole, user.getRole(), "Getter/Setter for Role should work");
    }

    @Test
    void shouldGetAndSetPassword() {
        User user = new User();
        String testPassword = "newSecurePassword456";
        user.setPassword(testPassword);
        assertEquals(testPassword, user.getPassword(), "Getter/Setter for Password should work");
    }

    @Test
    void shouldGetAndSetActive() {
        User user = new User();
        user.setActive(false);
        assertFalse(user.isActive(), "Getter/Setter for Active should work");
        user.setActive(true);
        assertTrue(user.isActive(), "Getter/Setter for Active should work");
    }

    @Test
    void shouldReturnCorrectBuilderToString() {
        User.UserBuilder builder = User.builder()
                .id("123456789")
                .idType(IdType.CC)
                .fullName("John Doe")
                .phone("1234567890")
                .email("john.doe@example.com")
                .role(Role.STUDENT)
                .password("securePassword123")
                .active(true);

        String builderToString = builder.toString();

        assertNotNull(builderToString, "Builder toString() should not return null");
        assertTrue(builderToString.contains("User.UserBuilder"), "Should contain builder class name");
        assertTrue(builderToString.contains("id=123456789"), "Should contain id");
        assertTrue(builderToString.contains("idType=CC"), "Should contain idType");
        assertTrue(builderToString.contains("fullName=John Doe"), "Should contain fullName");
        assertTrue(builderToString.contains("phone=1234567890"), "Should contain phone");
        assertTrue(builderToString.contains("email=john.doe@example.com"), "Should contain email");
        assertTrue(builderToString.contains("role=STUDENT"), "Should contain role");
        assertTrue(builderToString.contains("password=securePassword123"), "Should contain password");
        assertTrue(builderToString.contains("active=true"), "Should contain active status");
    }
}