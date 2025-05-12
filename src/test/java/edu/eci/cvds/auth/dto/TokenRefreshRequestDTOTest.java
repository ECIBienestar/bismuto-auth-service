package edu.eci.cvds.auth.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TokenRefreshRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateValidTokenRefreshRequestDTO() {
        TokenRefreshRequestDTO dto = new TokenRefreshRequestDTO("valid.refresh.token");
        Set<ConstraintViolation<TokenRefreshRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailWhenRefreshTokenIsBlank() {
        TokenRefreshRequestDTO dto = new TokenRefreshRequestDTO("");
        Set<ConstraintViolation<TokenRefreshRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals("Refresh token cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenRefreshTokenIsNull() {
        TokenRefreshRequestDTO dto = new TokenRefreshRequestDTO(null);
        Set<ConstraintViolation<TokenRefreshRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals("Refresh token cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void shouldHaveWorkingGettersAndSetters() {
        TokenRefreshRequestDTO dto = new TokenRefreshRequestDTO();
        dto.setRefreshToken("new.refresh.token");
        assertEquals("new.refresh.token", dto.getRefreshToken());
    }

    @Test
    void shouldHaveNoArgsConstructor() {
        TokenRefreshRequestDTO dto = new TokenRefreshRequestDTO();
        assertNull(dto.getRefreshToken());
    }

    @Test
    void shouldHaveAllArgsConstructor() {
        TokenRefreshRequestDTO dto = new TokenRefreshRequestDTO("constructor.token");
        assertEquals("constructor.token", dto.getRefreshToken());
    }

    @Test
    void shouldImplementEqualsCorrectly() {
        TokenRefreshRequestDTO dto1 = new TokenRefreshRequestDTO("token1");
        TokenRefreshRequestDTO dto2 = new TokenRefreshRequestDTO("token1");
        TokenRefreshRequestDTO dto3 = new TokenRefreshRequestDTO("token2");

        assertEquals(dto1, dto2);

        assertNotEquals(dto1, dto3);

        assertNotEquals(dto1, null);

        assertNotEquals(dto1, "I'm a String");
    }

    @Test
    void shouldImplementHashCodeCorrectly() {
        TokenRefreshRequestDTO dto1 = new TokenRefreshRequestDTO("token1");
        TokenRefreshRequestDTO dto2 = new TokenRefreshRequestDTO("token1");
        TokenRefreshRequestDTO dto3 = new TokenRefreshRequestDTO("token2");

        assertEquals(dto1.hashCode(), dto2.hashCode());

        assertNotEquals(dto1.hashCode(), dto3.hashCode());

        assertEquals(dto1.hashCode(), dto1.hashCode());
    }

    @Test
    void shouldImplementToString() {
        TokenRefreshRequestDTO dto = new TokenRefreshRequestDTO("test.token");
        String str = dto.toString();

        assertNotNull(str);
        assertTrue(str.contains("test.token"));
        assertTrue(str.contains("refreshToken"));
    }

    @Test
    void shouldHandleNullInEqualsAndHashCode() {
        TokenRefreshRequestDTO dto1 = new TokenRefreshRequestDTO(null);
        TokenRefreshRequestDTO dto2 = new TokenRefreshRequestDTO(null);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        TokenRefreshRequestDTO dto3 = new TokenRefreshRequestDTO("not.null");
        assertNotEquals(dto1, dto3);
    }
}