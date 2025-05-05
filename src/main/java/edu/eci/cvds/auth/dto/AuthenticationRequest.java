package edu.eci.cvds.auth.dto;

/**
 * Data Transfer Object (DTO) for user authentication requests.
 * <p>
 * Contains the credentials required for logging in: email and password.
 */
public class AuthenticationRequest {
    private String email;
    private String password;

    /**
     * Default constructor.
     */
    public AuthenticationRequest() {}

    /**
     * Parameterized constructor.
     *
     * @param email    the user's email address
     * @param password the user's password
     */
    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the email address.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
