package com.example.DAO;
import com.example.Interface.UserDao;
import com.example.Model.Client;
import com.example.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private Connection con;


    public UserDaoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public void createUser(User user) {
        String sql = "INSERT INTO users (email, password) VALUES ('" + user.getEmail() + "', '" + user.getPassword() + "')";
        try {
            if (con == null) {
                System.out.println("Connection to mysql is failed.");
                return;
            }
            // Tạo một Statement
            Statement stmt = con.createStatement();
            // Thực thi câu lệnh SQL
            int rowsAffected = stmt.executeUpdate(sql);

            if (rowsAffected > 0) {
                System.out.println("User created successfully.");
            } else {
                System.out.println("No user was created.");
            }
            // Đóng Statement
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error occurred while creating user:");
            e.printStackTrace();
        }
    }

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
    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("email"),rs.getString("username"),rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT users.id, users.username,users.email, users.age, " +
                "users.phoneNumber,client.borrowed_books " +
                "FROM users " +
                "LEFT JOIN client ON users.id = client.user_id";

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clients.add(new Client(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getString("phoneNumber"),
                        rs.getInt("borrowed_books")));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE users SET username = ?, password = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Delete user with id: "+ id + " successfully!!!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isEmailExists(String email) {
        String query = "SELECT * FROM users WHERE email = ?"; // Giả sử bảng của bạn tên là 'users'
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("From isEmailExists!!!");
                return rs.getInt(1) > 0; // Nếu số lượng lớn hơn 0, tức là email đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Nếu không có lỗi và không tìm thấy email
    }

    @Override
    public boolean isMatchAccount(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            // Thiết lập các tham số
            statement.setString(1, email);
            statement.setString(2, password);

            // Thực thi truy vấn
            ResultSet resultSet = statement.executeQuery();

            // Kiểm tra nếu có kết quả nào trả về
            return resultSet.next(); // Nếu có bản ghi nào khớp thì trả về true
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}