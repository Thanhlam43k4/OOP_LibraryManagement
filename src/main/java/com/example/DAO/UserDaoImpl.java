package com.example.DAO;

import com.example.Interface.UserDao;
import com.example.Model.Client;
import com.example.Model.User;
import com.example.Handlers.ExtraFunction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the UserDao interface for interacting with the database.
 */
public class UserDaoImpl implements UserDao {

    private final Connection con;

    /**
     * Constructor to initialize the database connection.
     *
     * @param con Database connection.
     */
    public UserDaoImpl(Connection con) {
        this.con = con;
    }

    /**
     * Creates a new user in the database.
     *
     * @param user The user object to be added.
     */
    @Override
    public void createUser(User user) {
        String sql = "INSERT INTO users (email, username, password) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2,user.getUsername());
            stmt.setString(3, user.getPassword());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User: " + user.getEmail() + " is created successfully.");
            } else {
                System.out.println("No user was created.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while creating user:");
            e.printStackTrace();
        }
    }

    @Override
    public void addUser (User user) {
        String sql = "INSERT INTO users (email, username, password, phoneNumber, age) VALUES (?, ?, ?, ?,?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2,user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4,user.getPhoneNumber());
            stmt.setInt(5,user.getAge());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User: " + user.getEmail() + " is created successfully.");
            } else {
                System.out.println("No user was created.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while creating user:");
            e.printStackTrace();
        }
    }
    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to be retrieved.
     * @return The user object or null if not found.
     */
    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email The email of the user to be retrieved.
     * @return The user object or null if not found.
     */
    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User u = new User(rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("role"));
                u.setPhoneNumber(rs.getString("phoneNumber"));
                u.setAge(rs.getInt("age"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all clients along with their borrowed books.
     *
     * @return A list of all clients.
     */
    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT users.id, users.username, users.email, users.age, " +
                "users.phoneNumber, client.borrowed_books " +
                "FROM users " +
                "LEFT JOIN client ON users.id = client.user_id";

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                username = (username == null) ? "Unknown" : username;

                String email = rs.getString("email");
                email = (email == null) ? "Unknown" : email;

                int age = rs.getInt("age");
                String phoneNumber = rs.getString("phoneNumber");
                phoneNumber = (phoneNumber == null) ? "Unknown" : phoneNumber;

                int borrowedBooks = rs.getInt("borrowed_books");
                if (rs.wasNull()) {
                    borrowedBooks = 0;
                }

                clients.add(new Client(id, username, email, age, phoneNumber, borrowedBooks));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    /**
     * Updates user details like username, phone number, and date of birth.
     *
     * @param userId    The ID of the user to update.
     * @param username  The new username.
     * @param phoneNumber The new phone number.
     */
    @Override
    public void updateUser(int userId, String username, String phoneNumber, int age) {
        String sql = "UPDATE users SET username = ?, phoneNumber = ?, age = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, phoneNumber);
            stmt.setInt(3, age);
            stmt.setInt(4, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a user from the database by their ID.
     *
     * @param id The ID of the user to be deleted.
     */
    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Deleted user with id: " + id + " successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if an email already exists in the users table.
     *
     * @param email The email to check for existence.
     * @return True if the email exists, false otherwise.
     */
    @Override
    public boolean isEmailExists(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // If the email exists, a result will be returned.
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Verifies if the provided email and password match an existing account.
     *
     * @param email    The user's email.
     * @return True if the email and password match, false otherwise.
     */
    @Override
    public boolean isMatchAccount(String email, String plainPassword) {
        String query = "SELECT password FROM users WHERE email = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            // Thiết lập tham số truy vấn
            statement.setString(1, email);
            // Thực thi truy vấn
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Lấy hashed password từ cơ sở dữ liệu
                String hashedPassword = resultSet.getString("password");
                // So sánh plainPassword với hashedPassword
                return ExtraFunction.decode(plainPassword,hashedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Trả về false nếu không tìm thấy tài khoản hoặc xảy ra lỗi
        return false;
    }
    /**
     * Adds a new user with the given details.
     *
     * @param email      The user's email.
     * @param username   The user's username.
     * @param phoneNumber The user's phone number.
     * @param age        The user's age.
     */
    @Override
    public void addUser(String email, String username, String phoneNumber, int age) {
        String query = "INSERT INTO users (email, username, phoneNumber, age) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setString(2, username);
            pstmt.setString(3, phoneNumber);
            pstmt.setInt(4, age);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User added successfully.");
            } else {
                System.out.println("Failed to add user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the number of borrowed books for a user by their ID.
     *
     * @param id The user's ID.
     * @return The number of borrowed books.
     */
    @Override
    public int getUserBooks(int id) {
        String query = "SELECT borrowed_books FROM client WHERE user_id = ?";
        int borrowedBooks = 0;
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    borrowedBooks = rs.getInt("borrowed_books");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowedBooks;
    }

    /**
     * Updates the password for a specific user by their ID.
     *
     * @param userId    The ID of the user to update.
     * @param updatePass The new password to set.
     */
    @Override
    public void updatePassword(int userId, String updatePass) {
        String hashPass = ExtraFunction.encode(updatePass);
        String query = "UPDATE users SET password = ? WHERE id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, hashPass);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
            System.out.println("Password updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Get Client by Email
     */
    @Override
    public Client getClientByEmail(String email){
        Client client = null;
        String query = "SELECT * FROM users WHERE email = ? AND role = 'client'";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            // Set the email parameter
            stmt.setString(1, email);

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Check if a result is returned
            if (rs.next()) {
                // Create Client object and map the result set to the Client fields
                client = new Client();
                client.setId(rs.getInt("id"));
                client.setEmail(rs.getString("email"));
                client.setUsername(rs.getString("username"));
                client.setAge(rs.getInt("age"));
                client.setPassword(rs.getString("password"));
                client.setPhoneNumber(rs.getString("phoneNumber"));
                client.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;  // Return the client object or null if not found
    }
}
