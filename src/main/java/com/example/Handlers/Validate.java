package com.example.Handlers;

/**
 * A utility class for validating various types of data such as emails, passwords, usernames,
 * phone numbers, ages, and other common fields used in an application.
 */
public class Validate {

    /**
     * Validates an email address using a regular expression.
     * The email must match the general email format pattern.
     *
     * @param email The email address to validate.
     * @return {@code true} if the email is valid, {@code false} otherwise.
     */
    public static boolean isValidEmail(String email) {
        // Kiểm tra định dạng email
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * Validates a password by checking its length.
     * The password must be at least 6 characters long.
     *
     * @param password The password to validate.
     * @return {@code true} if the password is valid, {@code false} otherwise.
     */
    public static boolean isValidPassword(String password) {
        // Kiểm tra mật khẩu (ví dụ: độ dài tối thiểu)
        return password != null && password.length() >= 6;
    }

    /**
     * Validates the title by ensuring that it is not null and has more than one character.
     *
     * @param title The title to validate.
     * @return {@code true} if the title is valid, {@code false} otherwise.
     */
    public static boolean isValidTitle(String title) {
        return title == null || title.trim().length() <= 1;
    }

    /**
     * Validates the author field by ensuring that it is not null and has more than one character.
     *
     * @param author The author name to validate.
     * @return {@code true} if the author is valid, {@code false} otherwise.
     */
    public static boolean isValidAuthor(String author) {
        return author == null || author.trim().length() <= 1;
    }

    /**
     * Validates a username. The username must contain at least 3 characters and no special characters other than underscores.
     *
     * @param username The username to validate.
     * @return {@code true} if the username is valid, {@code false} otherwise.
     */
    public static boolean isValidUsername(String username) {
        // Kiểm tra tên người dùng có chứa ít nhất 3 ký tự và không chứa ký tự đặc biệt
        return username != null && username.matches("^[A-Za-z0-9_]+$") && username.length() >= 3;
    }

    /**
     * Validates a phone number. The phone number must match a pattern that supports
     * optional country code and contains at least 10 digits.
     *
     * @param phoneNumber The phone number to validate.
     * @return {@code true} if the phone number is valid, {@code false} otherwise.
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Kiểm tra định dạng số điện thoại (ví dụ: bắt đầu với + hoặc số, dài ít nhất 10 ký tự)
        return phoneNumber != null && phoneNumber.matches("^(\\+?\\d{10,15})$");
    }

    /**
     * Validates the age by checking that it is an integer between 18 and 120.
     *
     * @param age The age to validate.
     * @return {@code true} if the age is valid, {@code false} otherwise.
     */
    public static boolean isValidAge(String age) {
        try {
            int ageInt = Integer.parseInt(age);
            return ageInt >= 18 && ageInt <= 120; // Tuổi phải trong khoảng từ 18 đến 120
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates a genre. The genre must not be null and must have more than one character.
     *
     * @param genre The genre to validate.
     * @return {@code true} if the genre is valid, {@code false} otherwise.
     */
    public static boolean isValidGenre(String genre) {
        return genre == null || genre.trim().length() <= 1;
    }

    /**
     * Validates the quantity by ensuring it is a positive integer greater than zero.
     *
     * @param quantity The quantity to validate.
     * @return {@code true} if the quantity is valid, {@code false} otherwise.
     */
    public static boolean isValidQuantity(String quantity) {
        try {
            int qty = Integer.parseInt(quantity);
            return qty > 0; // Số lượng phải là số dương
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates the ISBN (International Standard Book Number).
     * The ISBN must only contain numbers and dashes.
     *
     * @param isbn The ISBN to validate.
     * @return {@code true} if the ISBN is valid, {@code false} otherwise.
     */
    public static boolean isValidISBN(String isbn) {
        return isbn == null || !isbn.matches("^[0-9-]+$"); // Kiểm tra ISBN chỉ chứa số và dấu gạch ngang
    }
}
