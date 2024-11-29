package com.example.Service;

import com.example.Model.Admin;
import com.example.Model.Client;
import com.example.Model.User;

/**
 * The SessionManager class is responsible for managing user sessions.
 * It provides methods to track the currently logged-in user, determine if the user is an admin or client,
 * and clear the session when needed. The class follows the Singleton design pattern to ensure only one instance exists.
 */
public class SessionManager {

    // Singleton instance of SessionManager
    private static SessionManager instance;

    // The currently logged-in user
    private User loggedInUser;

    /**
     * Private constructor to prevent external instantiation of the class.
     * This class uses the Singleton design pattern to ensure that only one instance is created.
     */
    private SessionManager() {}

    /**
     * Returns the Singleton instance of the SessionManager class.
     * If the instance does not exist, it will be created.
     *
     * @return The singleton instance of the SessionManager.
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    /**
     * Sets the user that is currently logged in.
     *
     * @param user The user who is currently logged in.
     */
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    /**
     * Returns the user who is currently logged in.
     *
     * @return The currently logged-in user.
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Checks if the logged-in user is an Admin.
     *
     * @return true if the logged-in user is an instance of Admin, false otherwise.
     */
    public boolean isAdmin() {
        return loggedInUser instanceof Admin; // Checks if the logged-in user is an Admin
    }

    /**
     * Checks if the logged-in user is a Client.
     *
     * @return true if the logged-in user is an instance of Client, false otherwise.
     */
    public boolean isClient() {
        return loggedInUser instanceof Client; // Checks if the logged-in user is a Client
    }

    /**
     * Clears the session by setting the logged-in user to null.
     * This is typically called when the user logs out or the session is expired.
     */
    public void clearSession() {
        loggedInUser = null;
    }
}
