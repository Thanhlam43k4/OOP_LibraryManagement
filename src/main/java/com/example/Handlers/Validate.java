package com.example.Handlers;

public class Validate {
    public static boolean isValidEmail(String email) {
        // Kiểm tra định dạng email
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isValidPassword(String password) {
        // Kiểm tra mật khẩu (ví dụ: độ dài tối thiểu)
        return password != null && password.length() >= 6;
    }
}
