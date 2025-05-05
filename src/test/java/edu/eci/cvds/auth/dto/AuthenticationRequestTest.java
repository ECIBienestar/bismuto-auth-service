package edu.eci.cvds.auth.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationRequestTest {

    @Test
    public void testConstructorWithArgs() {
        String email = "test@example.com";
        String password = "123456";

        AuthenticationRequest request = new AuthenticationRequest(email, password);

        assertEquals(email, request.getEmail());
        assertEquals(password, request.getPassword());
    }

    @Test
    public void testSettersAndGetters() {
        AuthenticationRequest request = new AuthenticationRequest();

        request.setEmail("user@test.com");
        request.setPassword("password123");

        assertEquals("user@test.com", request.getEmail());
        assertEquals("password123", request.getPassword());
    }

    @Test
    public void testConstructorAndGetters() {
        AuthenticationRequest request = new AuthenticationRequest("test@example.com", "pass123");

        assertEquals("test@example.com", request.getEmail());
        assertEquals("pass123", request.getPassword());
    }

    @Test
    public void testSetters() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("another@example.com");
        request.setPassword("secure");

        assertEquals("another@example.com", request.getEmail());
        assertEquals("secure", request.getPassword());
    }
}
