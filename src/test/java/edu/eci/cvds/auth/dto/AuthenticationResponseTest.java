package edu.eci.cvds.auth.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationResponseTest {

    @Test
    public void testConstructorWithArgs() {
        String token = "abc.def.ghi";

        AuthenticationResponse response = new AuthenticationResponse(token);

        assertEquals(token, response.getToken());
    }

    @Test
    public void testSettersAndGetters() {
        AuthenticationResponse response = new AuthenticationResponse();

        response.setToken("xyz.123.token");

        assertEquals("xyz.123.token", response.getToken());
    }

    @Test
    public void testConstructorAndGetter() {
        AuthenticationResponse response = new AuthenticationResponse("mock-jwt-token");

        assertEquals("mock-jwt-token", response.getToken());
    }

    @Test
    public void testSetter() {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken("updated-token");

        assertEquals("updated-token", response.getToken());
    }
}
