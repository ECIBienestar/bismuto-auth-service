package edu.eci.cvds.auth.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserNotFoundExceptionTest {

    @Test
    public void testExceptionMessage() {
        String message = "User not found";
        UserNotFoundException exception = new UserNotFoundException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testExceptionIsRuntimeException() {
        UserNotFoundException exception = new UserNotFoundException("Error");
        assertTrue(exception instanceof RuntimeException);
    }
}
