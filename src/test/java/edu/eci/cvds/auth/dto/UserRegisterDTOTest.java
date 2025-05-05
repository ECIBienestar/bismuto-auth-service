package edu.eci.cvds.auth.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class UserRegisterDTOTest {

    // Test data with varied values
    private static final String[] TEST_NAMES = {"Camilo", "Lucía", "Andrés", "María", "Juan", "Ana"};
    private static final String[] TEST_EMAILS = {
            "camilo@example.com",
            "lucia@example.org",
            "andres@test.co",
            "maria@domain.net",
            "juan@mail.edu",
            "ana@web.io"
    };
    private static final String[] TEST_PASSWORDS = {
            "mypassword123",
            "securePass!456",
            "strongP@ss789",
            "veryS3cret!",
            "p@ssw0rd2023",
            "temp#12345"
    };

    @Test
    @DisplayName("Constructor with args should set all fields correctly")
    public void testConstructorWithArgs() {
        for (int i = 0; i < TEST_NAMES.length; i++) {
            UserRegisterDTO dto = new UserRegisterDTO(
                    TEST_PASSWORDS[i],
                    TEST_NAMES[i],
                    TEST_EMAILS[i]
            );

            assertEquals(TEST_PASSWORDS[i], dto.getPassword(),
                    "Password should match for index " + i);
            assertEquals(TEST_NAMES[i], dto.getName(),
                    "Name should match for index " + i);
            assertEquals(TEST_EMAILS[i], dto.getEmail(),
                    "Email should match for index " + i);
        }
    }

    @Test
    @DisplayName("Setters and getters should work correctly")
    public void testSettersAndGetters() {
        UserRegisterDTO dto = new UserRegisterDTO();

        for (int i = 0; i < TEST_NAMES.length; i++) {
            dto.setName(TEST_NAMES[i]);
            dto.setEmail(TEST_EMAILS[i]);
            dto.setPassword(TEST_PASSWORDS[i]);

            assertEquals(TEST_NAMES[i], dto.getName(),
                    "Name getter should match setter for index " + i);
            assertEquals(TEST_EMAILS[i], dto.getEmail(),
                    "Email getter should match setter for index " + i);
            assertEquals(TEST_PASSWORDS[i], dto.getPassword(),
                    "Password getter should match setter for index " + i);
        }
    }

    @Test
    @DisplayName("Equals and hashCode should follow contract")
    public void testEqualsAndHashCode() {
        // Test equality with same values
        for (int i = 0; i < TEST_NAMES.length; i++) {
            UserRegisterDTO user1 = new UserRegisterDTO(
                    TEST_PASSWORDS[i],
                    TEST_NAMES[i],
                    TEST_EMAILS[i]
            );
            UserRegisterDTO user2 = new UserRegisterDTO(
                    TEST_PASSWORDS[i],
                    TEST_NAMES[i],
                    TEST_EMAILS[i]
            );

            assertEquals(user1, user2,
                    "Objects with same values should be equal for index " + i);
            assertEquals(user1.hashCode(), user2.hashCode(),
                    "Hash codes should match for equal objects at index " + i);
        }

        // Test inequality with different values
        UserRegisterDTO baseUser = new UserRegisterDTO(
                TEST_PASSWORDS[0],
                TEST_NAMES[0],
                TEST_EMAILS[0]
        );

        for (int i = 1; i < TEST_NAMES.length; i++) {
            UserRegisterDTO differentUser = new UserRegisterDTO(
                    TEST_PASSWORDS[i],
                    TEST_NAMES[i],
                    TEST_EMAILS[i]
            );

            assertNotEquals(baseUser, differentUser,
                    "Objects with different values should not be equal for index " + i);
        }

        // Test against null and other types
        assertNotEquals(baseUser, null, "Should not equal null");
        assertNotEquals(baseUser, new Object(), "Should not equal different type");
    }

    @Test
    @DisplayName("toString should contain all field values")
    public void testToString() {
        for (int i = 0; i < TEST_NAMES.length; i++) {
            UserRegisterDTO user = new UserRegisterDTO(
                    TEST_PASSWORDS[i],
                    TEST_NAMES[i],
                    TEST_EMAILS[i]
            );

            String result = user.toString();

            assertTrue(result.contains(TEST_NAMES[i]),
                    "toString should contain name for index " + i);
            assertTrue(result.contains(TEST_EMAILS[i]),
                    "toString should contain email for index " + i);
            assertTrue(result.contains(TEST_PASSWORDS[i]),
                    "toString should contain password for index " + i);
        }
    }

    @Test
    @DisplayName("No-args constructor and setters should work")
    public void testNoArgsConstructorAndSetters() {
        UserRegisterDTO dto = new UserRegisterDTO();

        for (int i = 0; i < TEST_NAMES.length; i++) {
            dto.setName(TEST_NAMES[i]);
            dto.setEmail(TEST_EMAILS[i]);
            dto.setPassword(TEST_PASSWORDS[i]);

            assertEquals(TEST_NAMES[i], dto.getName(),
                    "Name should match after set for index " + i);
            assertEquals(TEST_EMAILS[i], dto.getEmail(),
                    "Email should match after set for index " + i);
            assertEquals(TEST_PASSWORDS[i], dto.getPassword(),
                    "Password should match after set for index " + i);
        }
    }

    @Test
    @DisplayName("Should handle null fields in equals and hashCode")
    public void testNullFieldHandling() {
        UserRegisterDTO nullName = new UserRegisterDTO("pass", null, "email1@test.com");
        UserRegisterDTO nullEmail = new UserRegisterDTO("pass", "name", null);
        UserRegisterDTO nullPassword = new UserRegisterDTO(null, "name", "email2@test.com");
        UserRegisterDTO allNull = new UserRegisterDTO(null, null, null);

        // Test equals with null fields
        assertEquals(nullName, new UserRegisterDTO("pass", null, "email1@test.com"));
        assertEquals(nullEmail, new UserRegisterDTO("pass", "name", null));
        assertEquals(nullPassword, new UserRegisterDTO(null, "name", "email2@test.com"));
        assertEquals(allNull, new UserRegisterDTO(null, null, null));

        // Test hashCode doesn't throw NPE
        assertDoesNotThrow(() -> nullName.hashCode());
        assertDoesNotThrow(() -> nullEmail.hashCode());
        assertDoesNotThrow(() -> nullPassword.hashCode());
        assertDoesNotThrow(() -> allNull.hashCode());
    }
}