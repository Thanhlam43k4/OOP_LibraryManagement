package com.example.Model;  // Tên package dùng chữ thường

/**
 * The User class represents a user with various details like id, username, email, age, phone number, password, and role.
 */
public class User {

    private int id;
    private String username;
    private String email;
    private int age;
    private String password;
    private String phoneNumber;
    private String role;

    /**
     * Constructor để tạo một đối tượng User với id, username và password.
     * @param id ID của người dùng.
     * @param username Tên người dùng.
     * @param password Mật khẩu của người dùng.
     */
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Constructor để tạo một đối tượng User với id, email, username, age và phoneNumber.
     * @param id ID của người dùng.
     * @param email Địa chỉ email của người dùng.
     * @param username Tên người dùng.
     * @param age Tuổi của người dùng.
     * @param phoneNumber Số điện thoại của người dùng.
     */
    public User(int id, String email, String username, int age, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Constructor để tạo một đối tượng User với email và password.
     * @param email Địa chỉ email của người dùng.
     * @param password Mật khẩu của người dùng.
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Constructor để tạo một đối tượng User với id, email, username và role.
     * @param id ID của người dùng.
     * @param email Địa chỉ email của người dùng.
     * @param username Tên người dùng.
     * @param role Vai trò của người dùng.
     */
    public User(int id, String email, String username, String role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;
    }

    /**
     * Constructor để tạo một đối tượng User với email, username, phoneNumber và age.
     * @param email Địa chỉ email của người dùng.
     * @param username Tên người dùng.
     * @param phoneNumber Số điện thoại của người dùng.
     * @param age Tuổi của người dùng.
     */
    public User(String email, String username, String phoneNumber, int age) {
        this.email = email;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }

    // Getter và Setter cho các thuộc tính
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Hiển thị thông tin người dùng.
     */
    public void display() {
        System.out.println("Username: " + username + " Password: " + password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
