package edu.eci.cvds.auth.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {
    private static final Logger logger = Logger.getLogger(JwtUtilTest.class.getName());
    private static final String VALID_SECRET = "thisIsA32CharacterLongSecretKey123456";
    private static final String INVALID_SECRET = "shortKey";
    private static final long TEST_EXPIRATION = 3600000;

    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil(VALID_SECRET, TEST_EXPIRATION);
    }



    @Test
    @DisplayName("Should generate valid token for valid email")
    void testGenerateAndValidateToken() {
        String email = "test2424@example.com";
        String token = jwtUtil.generateToken(email);

        logger.log(Level.INFO, "Generated token: {0}", token);

        assertNotNull(token, "Token should not be null");
        assertFalse(token.isEmpty(), "Token should not be empty string");
        assertEquals(3, token.split("\\.").length, "JWT should have 3 parts");

        String extractedUsername = jwtUtil.extractUsername(token);
        assertEquals(email, extractedUsername, "Extracted username should match original");

        assertTrue(jwtUtil.validateToken(token, email), "Token should be valid");

        Date expiration = jwtUtil.extractExpiration(token);
        assertTrue(expiration.after(new Date()), "Expiration should be in the future");
    }

    @Test
    @DisplayName("Should reject invalid token format")
    void testInvalidToken() {
        assertFalse(jwtUtil.validateToken("invalid.token", "test778@example.com"));
    }

    @Test
    @DisplayName("Should throw exception for invalid secret key length")
    void testInvalidSecretKeyLength() {
        assertThrows(IllegalArgumentException.class,
                () -> new JwtUtil(INVALID_SECRET, TEST_EXPIRATION),
                "Should throw exception for secret key shorter than 32 characters");
    }

    @Test
    @DisplayName("Should detect tampered token")
    void testTamperedToken() {
        String email = "test77@example.com";
        String token = jwtUtil.generateToken(email);

        String[] parts = token.split("\\.");
        String tamperedToken = parts[0] + "." + parts[1] + "tampered" + "." + parts[2];

        assertFalse(jwtUtil.validateToken(tamperedToken, email),
                "Tampered token should be invalid");
    }

    @Test
    @DisplayName("Should detect malformed token")
    void testMalformedToken() {
        assertFalse(jwtUtil.validateToken("not.a.valid.jwt", "test1414@example.com"),
                "Malformed token should be invalid");
    }

    @Test
    @DisplayName("Should reject token for wrong user")
    void testWrongUserToken() {
        String email = "test131@example.com";
        String wrongEmail = "wrong@example.com";
        String token = jwtUtil.generateToken(email);

        assertFalse(jwtUtil.validateToken(token, wrongEmail),
                "Token should be invalid for wrong user");
    }

    @Test
    @DisplayName("Should handle null or empty email in token generation")
    void testNullOrEmptyEmail() {
        String tokenForNull = jwtUtil.generateToken(null);
        assertNotNull(tokenForNull, "Token generation with null email should work");

        String tokenForEmpty = jwtUtil.generateToken("");
        assertNotNull(tokenForEmpty, "Token generation with empty email should work");
    }

    @Test
    @DisplayName("Should handle null or empty token in validation")
    void testNullOrEmptyTokenValidation() {
        assertFalse(jwtUtil.validateToken(null, "test@example.com"),
                "Should return false for null token");

        assertFalse(jwtUtil.validateToken("", "test@example.com"),
                "Should return false for empty token");
    }

    @Test
    @DisplayName("Should extract expiration date correctly")
    void testExtractExpiration() {
        String email = "test1313@example.com";
        String token = jwtUtil.generateToken(email);
        Date expiration = jwtUtil.extractExpiration(token);

        assertNotNull(expiration, "Expiration date should not be null");
        assertTrue(expiration.after(new Date()), "Expiration should be in the future");
    }

    @Test
    @DisplayName("Should throw exception when parsing invalid token")
    void testInvalidTokenParsing() {
        String invalidToken = "invalid.token";

        assertThrows(MalformedJwtException.class,
                () -> jwtUtil.extractUsername(invalidToken),
                "Should throw exception for invalid token");
    }
}