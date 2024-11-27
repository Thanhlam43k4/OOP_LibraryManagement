package com.example;

import com.example.DAO.UserDaoImpl;
import com.example.Model.User;
import com.example.Model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserDaoImplTest {

    private Connection mockConnection;
    private UserDaoImpl userDao;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        // Khởi tạo mock objects
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Giả lập hành vi của các đối tượng
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Khởi tạo đối tượng UserDaoImpl với mock connection
        userDao = new UserDaoImpl(mockConnection);
    }

    @Test
    void testCreateUser() throws SQLException {
        User user = new User("test@email.com", "password");

        // Giả lập Statement và executeUpdate
        Statement mockStatement = mock(Statement.class);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeUpdate(anyString())).thenReturn(1); // Giả lập rằng 1 dòng đã được cập nhật

        userDao.createUser(user); // Gọi phương thức

        verify(mockConnection, times(1)).createStatement(); // Kiểm tra Statement đã được tạo
        verify(mockStatement, times(1)).executeUpdate(anyString()); // Kiểm tra executeUpdate đã được gọi
    }

    @Test
    void testGetUserById() throws SQLException {
        int userId = 1;
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(userId);
        when(mockResultSet.getString("username")).thenReturn("testUser");
        when(mockResultSet.getString("password")).thenReturn("testPassword");

        User user = userDao.getUserById(userId);
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("testPassword", user.getPassword());
    }

    @Test
    void testGetUserByEmail() throws SQLException {
        String email = "test@email.com";
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("email")).thenReturn(email);
        when(mockResultSet.getString("username")).thenReturn("testUser");
        when(mockResultSet.getString("role")).thenReturn("admin");

        User user = userDao.getUserByEmail(email);
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("test@email.com", user.getEmail());
        assertEquals("testUser", user.getUsername());
    }

    @Test
    void testGetAllClients() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mock(Statement.class));
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false); // Giả lập có một kết quả

        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("username")).thenReturn("testClient");
        when(mockResultSet.getString("email")).thenReturn("test@email.com");
        when(mockResultSet.getInt("age")).thenReturn(25);
        when(mockResultSet.getString("phoneNumber")).thenReturn("1234567890");
        when(mockResultSet.getInt("borrowed_books")).thenReturn(3);

        List<Client> user = userDao.getAllClients();
        assertNotNull(user);
        assertEquals(1, user.size());
        assertEquals("testClient", user.get(0).getUsername());
    }

    @Test
    void testUpdateUser() throws SQLException {
        int userId = 1;
        String username = "updatedUser";
        String phoneNumber = "0987654321";
        Date dob = Date.valueOf("1990-01-01");

        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Giả lập update thành công

        userDao.updateUser(userId, username, phoneNumber, age);

        verify(mockPreparedStatement, times(1)).setString(1, username);
        verify(mockPreparedStatement, times(1)).setString(2, phoneNumber);
        verify(mockPreparedStatement, times(1)).setDate(3, dob);
        verify(mockPreparedStatement, times(1)).setInt(4, userId);
        verify(mockPreparedStatement, times(1)).executeUpdate(); // Kiểm tra phương thức update đã được gọi
    }

    @Test
    void testDeleteUser() throws SQLException {
        int userId = 1;

        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Giả lập xóa thành công

        userDao.deleteUser(userId);

        verify(mockPreparedStatement, times(1)).setInt(1, userId);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testIsEmailExists() throws SQLException {
        String email = "test@email.com";

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(false); // Không tìm thấy email

        boolean exists = userDao.isEmailExists(email);


        assertFalse(exists);
    }

    @Test
    void testIsMatchAccount() throws SQLException {
        String email = "test@email.com";
        String password = "password";
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true); // Giả lập tìm thấy kết quả

        boolean isMatch = userDao.isMatchAccount(email, password);

        assertTrue(isMatch);
    }

    @Test
    void testAddUser() throws SQLException {
        String email = "newUser@email.com";
        String username = "newUser";
        String phoneNumber = "1234567890";
        int age = 30;

        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Giả lập add thành công

        userDao.addUser(email, username, phoneNumber, age);

        verify(mockPreparedStatement, times(1)).setString(1, email);
        verify(mockPreparedStatement, times(1)).setString(2, username);
        verify(mockPreparedStatement, times(1)).setString(3, phoneNumber);
        verify(mockPreparedStatement, times(1)).setInt(4, age);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testGetUserBooks() throws SQLException {
        int userId = 1;
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("borrowed_books")).thenReturn(5);

        int borrowedBooks = userDao.getUserBooks(userId);

        assertEquals(5, borrowedBooks);
    }

    @Test
    void testUpdatePassword() throws SQLException {
        int userId = 1;
        String newPassword = "newPassword123";

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        userDao.updatePassword(userId, newPassword);

        verify(mockPreparedStatement, times(1)).setString(1, newPassword);
        verify(mockPreparedStatement, times(1)).setInt(2, userId);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }
}
