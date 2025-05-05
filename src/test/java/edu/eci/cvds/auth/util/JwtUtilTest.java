package edu.eci.cvds.auth.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {
    private static final Logger logger = Logger.getLogger(JwtUtilTest.class.getName());
    private static final String VALID_SECRET = "thisIsA32CharacterLongSecretKey123456";
    private static final long TEST_EXPIRATION = 3600000; // 1 hour

    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil(VALID_SECRET, TEST_EXPIRATION);
    }

    @Test
    void testGenerateAndValidateToken() {
        String email = "test@example.com";
        String token = jwtUtil.generateToken(email);

        // Debug output
        logger.log(Level.INFO, "Generated token: {0}", token);

        // Assertions
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
    void testInvalidToken() {
        assertFalse(jwtUtil.validateToken("invalid.token", "test@example.com"));
    }
}