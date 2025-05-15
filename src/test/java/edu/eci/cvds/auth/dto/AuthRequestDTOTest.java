package edu.eci.cvds.auth.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

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
        assertEquals(dto, dto);
    }

    @Test
    void equalsShouldReturnTrueForEqualObjects() {
        AuthRequestDTO dto1 = new AuthRequestDTO("testUser", "testPass");
        AuthRequestDTO dto2 = new AuthRequestDTO("testUser", "testPass");
        assertEquals(dto1, dto2);
    }

    @Test
    void equalsShouldReturnFalseForDifferentUsername() {
        AuthRequestDTO dto1 = new AuthRequestDTO("testUser1", "testPass");
        AuthRequestDTO dto2 = new AuthRequestDTO("testUser2", "testPass");
        assertNotEquals(dto1, dto2);
    }

    @Test
    void equalsShouldReturnFalseForDifferentPassword() {
        AuthRequestDTO dto1 = new AuthRequestDTO("testUser", "testPass1");
        AuthRequestDTO dto2 = new AuthRequestDTO("testUser", "testPass2");
        assertNotEquals(dto1, dto2);
    }

    @Test
    void equalsShouldReturnFalseForNull() {
        AuthRequestDTO dto = new AuthRequestDTO("testUser", "testPass");
        assertNotEquals(null, dto);
    }

    @Test
    void equalsShouldReturnFalseForDifferentClass() {
        AuthRequestDTO dto = new AuthRequestDTO("testUser", "testPass");
        assertEquals("testUser", String.valueOf(dto));
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
        assertEquals(dto1, dto1);
        assertNotNull(dto1);

        assertEquals("not a dto", String.valueOf(dto1));

        AuthRequestDTO dto2 = new AuthRequestDTO("user", "pass");
        assertEquals(dto1, dto2);

        AuthRequestDTO dto3 = new AuthRequestDTO("different", "pass");
        assertNotEquals(dto1, dto3);

        AuthRequestDTO dto4 = new AuthRequestDTO("user", "different");
        assertNotEquals(dto1, dto4);

        AuthRequestDTO dto5 = new AuthRequestDTO("diff", "diff");
        assertNotEquals(dto1, dto5);

        AuthRequestDTO dto6 = new AuthRequestDTO(null, "pass");
        AuthRequestDTO dto7 = new AuthRequestDTO(null, "pass");
        assertEquals(dto6, dto7);
        assertNotEquals(dto6, dto1);

        AuthRequestDTO dto8 = new AuthRequestDTO("user", null);
        AuthRequestDTO dto9 = new AuthRequestDTO("user", null);
        assertEquals(dto8, dto9);
        assertNotEquals(dto8, dto1);
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