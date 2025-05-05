package edu.eci.cvds.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for registering a new user.
 * <p>
 * Contains the necessary fields to create a user: name, email, and password.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    private String password;
    private String name;
    private String email;

    /**
     * Returns the user's name.
     *
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Returns the user's email address.
     *
     * @return the email
     */
    public String getEmail() { return email; }

    /**
     * Returns the user's password.
     *
     * @return the password
     */
    public String getPassword() { return password; }

    /**
     * Sets the user's name.
     *
     * @param name the name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * Sets the user's email address.
     *
     * @param email the email to set
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Sets the user's password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) { this.password = password; }
}
