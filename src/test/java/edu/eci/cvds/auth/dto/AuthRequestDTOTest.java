package edu.eci.cvds.auth.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AuthRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateValidAuthRequestDTO() {
        AuthRequestDTO dto = new AuthRequestDTO("testUser", "validPassword123");
        Set<ConstraintViolation<AuthRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailWhenUsernameIsBlank() {
        AuthRequestDTO dto = new AuthRequestDTO("", "validPassword123");
        Set<ConstraintViolation<AuthRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Username cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenPasswordIsBlank() {
        AuthRequestDTO dto = new AuthRequestDTO("testUser", "");
        Set<ConstraintViolation<AuthRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Password cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenBothFieldsAreBlank() {
        AuthRequestDTO dto = new AuthRequestDTO("", "");
        Set<ConstraintViolation<AuthRequestDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
    }

    @Test
    void shouldHaveCorrectGettersAndSetters() {
        AuthRequestDTO dto = new AuthRequestDTO();
        dto.setUsername("newUser");
        dto.setPassword("newPassword123");

        assertEquals("newUser", dto.getUsername());
        assertEquals("newPassword123", dto.getPassword());
    }

    @Test
    void shouldHaveAllArgsConstructor() {
        AuthRequestDTO dto = new AuthRequestDTO("testUser", "testPass");
        assertEquals("testUser", dto.getUsername());
        assertEquals("testPass", dto.getPassword());
    }

    @Test
    void shouldHaveNoArgsConstructor() {
        AuthRequestDTO dto = new AuthRequestDTO();
        assertNull(dto.getUsername());
        assertNull(dto.getPassword());
    }

    @Test
    void shouldHaveDataAnnotation() {
        AuthRequestDTO dto1 = new AuthRequestDTO("user1", "pass1");
        AuthRequestDTO dto2 = new AuthRequestDTO("user1", "pass1");
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotNull(dto1.toString());
    }

    @Test
    void equalsShouldReturnTrueForSameObject() {
        AuthRequestDTO dto = new AuthRequestDTO("testUser", "testPass");
        assertTrue(dto.equals(dto));
    }

    @Test
    void equalsShouldReturnTrueForEqualObjects() {
        AuthRequestDTO dto1 = new AuthRequestDTO("testUser", "testPass");
        AuthRequestDTO dto2 = new AuthRequestDTO("testUser", "testPass");
        assertTrue(dto1.equals(dto2));
    }

    @Test
    void equalsShouldReturnFalseForDifferentUsername() {
        AuthRequestDTO dto1 = new AuthRequestDTO("testUser1", "testPass");
        AuthRequestDTO dto2 = new AuthRequestDTO("testUser2", "testPass");
        assertFalse(dto1.equals(dto2));
    }

    @Test
    void equalsShouldReturnFalseForDifferentPassword() {
        AuthRequestDTO dto1 = new AuthRequestDTO("testUser", "testPass1");
        AuthRequestDTO dto2 = new AuthRequestDTO("testUser", "testPass2");
        assertFalse(dto1.equals(dto2));
    }

    @Test
    void equalsShouldReturnFalseForNull() {
        AuthRequestDTO dto = new AuthRequestDTO("testUser", "testPass");
        assertFalse(dto.equals(null));
    }

    @Test
    void equalsShouldReturnFalseForDifferentClass() {
        AuthRequestDTO dto = new AuthRequestDTO("testUser", "testPass");
        assertFalse(dto.equals("testUser"));
    }

    @Test
    void hashCodeShouldBeEqualForEqualObjects() {
        AuthRequestDTO dto1 = new AuthRequestDTO("testUser", "testPass");
        AuthRequestDTO dto2 = new AuthRequestDTO("testUser", "testPass");
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void hashCodeShouldBeDifferentForDifferentUsername() {
        AuthRequestDTO dto1 = new AuthRequestDTO("testUser1", "testPass");
        AuthRequestDTO dto2 = new AuthRequestDTO("testUser2", "testPass");
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void hashCodeShouldBeDifferentForDifferentPassword() {
        AuthRequestDTO dto1 = new AuthRequestDTO("testUser", "testPass1");
        AuthRequestDTO dto2 = new AuthRequestDTO("testUser", "testPass2");
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void hashCodeShouldBeConsistent() {
        AuthRequestDTO dto = new AuthRequestDTO("testUser", "testPass");
        int initialHashCode = dto.hashCode();
        assertEquals(initialHashCode, dto.hashCode());
    }

    @Test
    void equalsShouldHandleAllBranches() {
        AuthRequestDTO dto1 = new AuthRequestDTO("user", "pass");
        assertTrue(dto1.equals(dto1));

        assertFalse(dto1.equals(null));

        assertFalse(dto1.equals("not a dto"));

        AuthRequestDTO dto2 = new AuthRequestDTO("user", "pass");
        assertTrue(dto1.equals(dto2));

        AuthRequestDTO dto3 = new AuthRequestDTO("different", "pass");
        assertFalse(dto1.equals(dto3));

        AuthRequestDTO dto4 = new AuthRequestDTO("user", "different");
        assertFalse(dto1.equals(dto4));

        AuthRequestDTO dto5 = new AuthRequestDTO("diff", "diff");
        assertFalse(dto1.equals(dto5));

        AuthRequestDTO dto6 = new AuthRequestDTO(null, "pass");
        AuthRequestDTO dto7 = new AuthRequestDTO(null, "pass");
        assertTrue(dto6.equals(dto7));
        assertFalse(dto6.equals(dto1));

        AuthRequestDTO dto8 = new AuthRequestDTO("user", null);
        AuthRequestDTO dto9 = new AuthRequestDTO("user", null);
        assertTrue(dto8.equals(dto9));
        assertFalse(dto8.equals(dto1));
    }

    @Test
    void hashCodeShouldHandleAllCases() {
        AuthRequestDTO dto1 = new AuthRequestDTO("user", "pass");
        AuthRequestDTO dto2 = new AuthRequestDTO("user", "pass");
        assertEquals(dto1.hashCode(), dto2.hashCode());

        AuthRequestDTO dto3 = new AuthRequestDTO(null, "pass");
        AuthRequestDTO dto4 = new AuthRequestDTO(null, "pass");
        assertEquals(dto3.hashCode(), dto4.hashCode());

        AuthRequestDTO dto5 = new AuthRequestDTO("user", null);
        AuthRequestDTO dto6 = new AuthRequestDTO("user", null);
        assertEquals(dto5.hashCode(), dto6.hashCode());

        AuthRequestDTO dto7 = new AuthRequestDTO(null, null);
        AuthRequestDTO dto8 = new AuthRequestDTO(null, null);
        assertEquals(dto7.hashCode(), dto8.hashCode());

        AuthRequestDTO dto9 = new AuthRequestDTO("user1", "pass1");
        AuthRequestDTO dto10 = new AuthRequestDTO("user2", "pass2");
        assertNotEquals(dto9.hashCode(), dto10.hashCode());
    }
}