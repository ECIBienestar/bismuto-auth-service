package edu.eci.cvds.auth.dto;

/**
 * Data Transfer Object (DTO) used to return a JWT token
 * in response to a successful authentication request.
 */
public class AuthenticationResponse {
    private String token;

    /**
     * Default constructor.
     */
    public AuthenticationResponse() {}

    /**
     * Parameterized constructor.
     *
     * @param token the JWT token generated after successful authentication
     */
    public AuthenticationResponse(String token) {
        this.token = token;
    }

    /**
     * Returns the JWT token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the JWT token.
     *
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }
}
