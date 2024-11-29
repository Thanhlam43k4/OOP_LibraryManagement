package com.example.Model;

/**
 * The {@code Admin} class represents an admin user in the system.
 * It extends the {@link User} class and overrides the {@link User#getRole()} method 
 * to return the role as "admin".
 *
 * @see User
 */
public class Admin extends User {

    /**
     * Constructs an {@code Admin} object with the specified email and password.
     *
     * @param email    the email of the admin
     * @param password the password of the admin
     */
    public Admin(String email, String password) {
        super(email, password);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns the role of the user as "admin".
     * </p>
     *
     * @return the role of the user, which is "admin"
     */
    @Override
    public String getRole() {
        return "admin";
    }
}
