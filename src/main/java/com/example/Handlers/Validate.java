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
    public static boolean isValidTitle(String title) {
        return title == null || title.trim().length() <= 1;
    }

    public static boolean isValidAuthor(String author) {
        return author == null || author.trim().length() <= 1;
    }

    public static boolean isValidUsername(String username) {
        // Kiểm tra tên người dùng có chứa ít nhất 3 ký tự và không chứa ký tự đặc biệt
        return username != null && username.matches("^[A-Za-z0-9_]+$") && username.length() >= 3;
    }
    // Kiểm tra tính hợp lệ của số điện thoại
    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Kiểm tra định dạng số điện thoại (ví dụ: bắt đầu với + hoặc số, dài ít nhất 10 ký tự)
        return phoneNumber != null && phoneNumber.matches("^(\\+?\\d{10,15})$");
    }
    // Kiểm tra tính hợp lệ của tuổi
    public static boolean isValidAge(String age) {
        try {
            int ageInt = Integer.parseInt(age);
            return ageInt >= 18 && ageInt <= 120; // Tuổi phải trong khoảng từ 18 đến 120
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isValidGenre(String genre) {
        return genre == null || genre.trim().length() <= 1;
    }

    public static boolean isValidQuantity(String quantity) {
        try {
            int qty = Integer.parseInt(quantity);
            return qty > 0; // Số lượng phải là số dương
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidISBN(String isbn) {
        return isbn == null || !isbn.matches("^[0-9-]+$"); // Kiểm tra ISBN chỉ chứa số và dấu gạch ngang
    }
}
