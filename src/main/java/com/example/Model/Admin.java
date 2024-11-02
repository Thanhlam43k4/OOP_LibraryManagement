package com.example.Model;

public class Admin extends User {
    public Admin(String email, String password) {
        super(email, password);
    }
    @Override
    public String getRole() {
        return "admin";
    }
}
