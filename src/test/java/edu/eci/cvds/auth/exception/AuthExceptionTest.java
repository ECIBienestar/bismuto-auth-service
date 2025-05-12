package edu.eci.cvds.auth.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

class AuthExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        String errorMessage = "Authentication failed";

        AuthException exception = new AuthException(errorMessage);

        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
        assertNull(exception.getCause());

        ResponseStatus responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
        assertNotNull(responseStatus);
        assertEquals(HttpStatus.UNAUTHORIZED, responseStatus.value());
    }

    @Test
    void shouldCreateExceptionWithMessageAndCause() {
        String errorMessage = "Authentication failed";
        Throwable cause = new RuntimeException("Root cause");

        AuthException exception = new AuthException(errorMessage, cause);

        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());

        ResponseStatus responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
        assertNotNull(responseStatus);
        assertEquals(HttpStatus.UNAUTHORIZED, responseStatus.value());
    }

    @Test
    void shouldHaveCorrectResponseStatus() {
        ResponseStatus responseStatus = AuthException.class.getAnnotation(ResponseStatus.class);

        assertNotNull(responseStatus);
        assertEquals(HttpStatus.UNAUTHORIZED, responseStatus.value());
        assertEquals("UNAUTHORIZED", responseStatus.value().name());
        assertEquals(401, responseStatus.value().value());
    }
}