package edu.eci.cvds.auth.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserResponseDTOTest {

    @Test
    public void testAllArgsConstructorAndGetters() {
        UserResponseDTO dto = new UserResponseDTO("1", "Camila", "camila@example.com");

        assertEquals("1", dto.getId());
        assertEquals("Camila", dto.getName());
        assertEquals("camila@example.com", dto.getEmail());
    }

    @Test
    public void testNoArgsConstructorAndSetters() {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId("2");
        dto.setName("Andrés");
        dto.setEmail("andres@example.com");

        assertEquals("2", dto.getId());
        assertEquals("Andrés", dto.getName());
        assertEquals("andres@example.com", dto.getEmail());
    }

    @Test
    public void testEqualsAndHashCode() {
        UserResponseDTO dto1 = new UserResponseDTO("3", "Laura", "laura@example.com");
        UserResponseDTO dto2 = new UserResponseDTO("3", "Laura", "laura@example.com");
        UserResponseDTO dto3 = new UserResponseDTO("4", "Carlos", "carlos@example.com");

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void testToString() {
        UserResponseDTO dto = new UserResponseDTO("5", "Sofía", "sofia@example.com");

        String result = dto.toString();

        assertTrue(result.contains("5"));
        assertTrue(result.contains("Sofía"));
        assertTrue(result.contains("sofia@example.com"));
    }
}
