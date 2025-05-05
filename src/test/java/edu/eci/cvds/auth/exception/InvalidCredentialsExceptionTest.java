package edu.eci.cvds.auth.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InvalidCredentialsExceptionTest {

    @Test
    public void testExceptionMessage() {
        String message = "Invalid email or password";
        InvalidCredentialsException exception = new InvalidCredentialsException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testExceptionIsRuntimeException() {
        InvalidCredentialsException exception = new InvalidCredentialsException("Test");
        assertTrue(exception instanceof RuntimeException);
    }
}
