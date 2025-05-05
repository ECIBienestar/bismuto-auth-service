package edu.eci.cvds.auth.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testAllArgsConstructorAndGetters() {
        User user = new User("1", "Camila", "camila@example.com", "password123", Role.STUDENT);

        assertEquals("1", user.getId());
        assertEquals("Camila", user.getName());
        assertEquals("camila@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(Role.STUDENT, user.getRole());
    }

    @Test
    public void testNoArgsConstructorAndSetters() {
        User user = new User();
        user.setId("2");
        user.setName("Andrés");
        user.setEmail("andres@example.com");
        user.setPassword("securepass");
        user.setRole(Role.ADMIN);

        assertEquals("2", user.getId());
        assertEquals("Andrés", user.getName());
        assertEquals("andres@example.com", user.getEmail());
        assertEquals("securepass", user.getPassword());
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    public void testEqualsAndHashCode() {
        User user1 = new User("3", "Laura", "laura@example.com", "pass123", Role.STUDENT);
        User user2 = new User("3", "Laura", "laura@example.com", "pass123", Role.STUDENT);
        User user3 = new User("4", "Carlos", "carlos@example.com", "pass456", Role.ADMIN);

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testToString() {
        User user = new User("5", "Sofía", "sofia@example.com", "pass", Role.STUDENT);
        String result = user.toString();

        assertTrue(result.contains("5"));
        assertTrue(result.contains("Sofía"));
        assertTrue(result.contains("sofia@example.com"));
        assertTrue(result.contains("STUDENT"));
    }
}