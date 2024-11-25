package com.example.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class provides methods to establish and manage the connection to a MySQL database.
 * It includes functionality for creating a connection to the database and closing the connection.
 */
public class DatabaseConnection {

    /**
     * The database URL for the MySQL connection.
     * Format: jdbc:mysql://<host>:<port>/<database_name>
     */
    private static final String URL = "jdbc:mysql://localhost:3306/users"; // Change this to the correct database URL

    /**
     * The username for the MySQL connection.
     */
    private static final String USER = "root"; // MySQL username

    /**
     * The password for the MySQL connection.
     */
    private static final String PASSWORD = "lamcoivodoi123"; // MySQL password

    /**
     * The database connection object.
     */
    private static Connection connection;

    /**
     * Retrieves the database connection. If the connection has not yet been established,
     * it will create a new one.
     *
     * @return The current connection to the MySQL database.
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connect Database successfully");
            } catch (SQLException e) {
                System.out.println("Error connecting to database: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * Closes the current database connection, if it is open.
     * This method should be called when the connection is no longer needed.
     */
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
