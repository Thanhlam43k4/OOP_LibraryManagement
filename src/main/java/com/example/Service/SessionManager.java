package com.example.Service;

import com.example.Model.Admin;
import com.example.Model.Client;
import com.example.Model.User;

public class SessionManager {
    private static SessionManager instance;
    private User loggedInUser;
    private SessionManager() {

    }
    public static SessionManager getInstance() {
        if(instance == null) {
            instance = new SessionManager();
        }
        return instance;

    }
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }
    public User getLoggedInUser() {
        return loggedInUser;
    }
    public boolean isAdmin() {
        return loggedInUser instanceof Admin; // Kiểm tra xem người dùng có phải là Admin không
    }

    public boolean isClient() {
        return loggedInUser instanceof Client; // Kiểm tra xem người dùng có phải là Client không
    }
    public void clearSession() {
        loggedInUser = null;
    }
}
