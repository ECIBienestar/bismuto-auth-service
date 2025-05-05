package edu.eci.cvds.auth.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserAlreadyExistsExceptionTest {

    @Test
    public void testExceptionMessage() {
        String message = "User already exists";
        UserAlreadyExistsException exception = new UserAlreadyExistsException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testExceptionIsRuntimeException() {
        UserAlreadyExistsException exception = new UserAlreadyExistsException("Duplicate");
        assertTrue(exception instanceof RuntimeException);
    }
}
