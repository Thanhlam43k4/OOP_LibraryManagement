package com.example.Interface;

import com.example.Model.Client;
import com.example.Model.User;

import java.sql.Date;
import java.util.List;

/**
 * This interface defines the data access methods for managing user-related operations, including
 * creating, retrieving, updating, and deleting user accounts. It also provides methods for validating
 * user existence and checking credentials.
 */
public interface UserDao {


    void addUser(User user);
    /**
     * Creates a new user account in the system.
     *
     * @param user The user object containing the details to be added.
     */
    void createUser(User user);

    /**
     * Retrieves a user by their unique ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user with the specified ID, or {@code null} if no such user exists.
     */
    User getUserById(int userId);

    /**
     * Retrieves a list of all clients in the system.
     *
     * @return A list of all clients.
     */
    List<Client> getAllClients();

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address of the user to retrieve.
     * @return The user with the specified email, or {@code null} if no such user exists.
     */
    User getUserByEmail(String email);

    /**
     * Updates a user's information such as username, phone number, and date of birth.
     *
     * @param userId      The ID of the user to update.
     * @param username    The new username to set.
     * @param phoneNumber The new phone number to set.
     * @param dob         The new date of birth to set.
     */
    void updateUser(int userId, String username, String phoneNumber, Date dob);

    /**
     * Deletes a user from the system.
     *
     * @param userId The ID of the user to delete.
     */
    void deleteUser(int userId);

    /**
     * Checks if an email address already exists in the system.
     *
     * @param email The email address to check.
     * @return {@code true} if the email exists, {@code false} otherwise.
     */
    boolean isEmailExists(String email);

    /**
     * Checks if the provided email and password match an existing account in the system.
     *
     * @param email    The email address to check.
     * @param password The password to check.
     * @return {@code true} if the credentials match, {@code false} otherwise.
     */
    boolean isMatchAccount(String email, String password);

    /**
     * Adds a new user to the system using basic details (email, username, phone number, and age).
     *
     * @param email       The email address of the new user.
     * @param username    The username of the new user.
     * @param phoneNumber The phone number of the new user.
     * @param age         The age of the new user.
     */
    void addUser(String email, String username, String phoneNumber, int age);

    /**
     * Retrieves the number of books currently borrowed by a user.
     *
     * @param userId The ID of the user to check.
     * @return The number of books borrowed by the user.
     */
    int getUserBooks(int userId);

    /**
     * Updates the password of a user.
     *
     * @param userId    The ID of the user whose password is being updated.
     * @param updatePass The new password to set.
     */
    void updatePassword(int userId, String updatePass);
}
