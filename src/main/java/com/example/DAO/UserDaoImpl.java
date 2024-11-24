package com.example.DAO;
import com.example.Interface.UserDao;
import com.example.Model.Client;
import com.example.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private final Connection con;

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
                System.out.println("User: "+  user.getEmail() + "is created successfully.");
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
    public void updateUser(int userId,String username, String phoneNumber, Date dob) {
        String sql = "UPDATE users SET username = ?, phoneNumber = ?, dob = ?  WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, phoneNumber);
            stmt.setDate(3,dob );
            stmt.setInt(4,userId);

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

    @Override
    public void addUser(String email, String username, String phoneNumber, int age){
        String query = "INSERT INTO users (email, username, phoneNumber, age) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {

            // Thiết lập các tham số cho câu lệnh
            pstmt.setString(1, email);
            pstmt.setString(2, username);
            pstmt.setString(3, phoneNumber);
            pstmt.setInt(4, age);

            // Thực thi câu lệnh INSERT
            int rowsAffected = pstmt.executeUpdate();

            // Kiểm tra xem câu lệnh có thành công không
            if (rowsAffected > 0) {
                System.out.println("User added successfully.");
            } else {
                System.out.println("Failed to add user.");
            }
        } catch (SQLException e) {
            // Xử lý lỗi khi kết nối hoặc thực thi câu truy vấn
            e.printStackTrace();
        }
    }

    @Override
    public int getUserBooks(int id) {
        String query = "SELECT borrowed_books FROM client WHERE user_id = ?";
        int borrowedBooks = 0;
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    borrowedBooks = rs.getInt("borrowed_books"); // Lấy giá trị từ cột borrowed_books
                }
            }
        } catch (SQLException e) {
            // Xử lý lỗi khi kết nối hoặc thực thi câu truy vấn
            e.printStackTrace();
        }

        return borrowedBooks; // Trả về số sách mượn
    }

    @Override
    public void updatePassword(int userId, String updatePass) {
        String query = "UPDATE users SET password = ? WHERE user_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1,updatePass);
            pstmt.setInt(2,userId);
            pstmt.executeQuery();
            System.out.println("Update Password Successfully!!!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}