package com.example.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/users"; // Thay đổi đường dẫn
    private static final String USER = "root"; // Tên người dùng MySQL
    private static final String PASSWORD = "";//lamcoivodoi123"; // Mật khẩu MySQL

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL,USER,PASSWORD);

                System.out.println("Connect Database successfully");
            } catch (SQLException e) {
                System.out.println("Error connecting to database: " + e.getMessage());

                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}