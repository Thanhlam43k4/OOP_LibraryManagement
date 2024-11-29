package com.example.DaoImplTest;

import com.example.DAO.UserDaoImpl;
import com.example.Model.Client;
import com.example.Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDaoImplTest {

    private Connection mockConnection;
    private UserDaoImpl userDao;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        userDao = new UserDaoImpl(mockConnection);
    }

    @Test
    void testCreateUser() throws SQLException {
        User user = new User("test@email.com", "testUser", "password");

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        userDao.createUser(user);

        verify(mockPreparedStatement, times(1)).setString(1, "test@email.com");
        verify(mockPreparedStatement, times(1)).setString(2, "testUser");
        verify(mockPreparedStatement, times(1)).setString(3, "password");
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testAddUser() throws SQLException {
        User user = new User("test@email.com", "testUser", "password", "1234567890", 30);

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        userDao.addUser(user);

        verify(mockPreparedStatement, times(1)).setString(1, "test@email.com");
        verify(mockPreparedStatement, times(1)).setString(2, "testUser");
        verify(mockPreparedStatement, times(1)).setString(3, "password");
        verify(mockPreparedStatement, times(1)).setString(4, "1234567890");
        verify(mockPreparedStatement, times(1)).setInt(5, 30);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testGetUserById() throws SQLException {
        int userId = 1;
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(userId);
        when(mockResultSet.getString("username")).thenReturn("testUser");
        when(mockResultSet.getString("password")).thenReturn("password");

        User user = userDao.getUserById(userId);

        assertNotNull(user);
        assertEquals(userId, user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("password", user.getPassword());
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
        assertEquals("admin", user.getRole());
    }

    @Test
    void testGetAllClients() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);

        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("username")).thenReturn("clientUser");
        when(mockResultSet.getString("email")).thenReturn("client@email.com");
        when(mockResultSet.getInt("age")).thenReturn(25);
        when(mockResultSet.getString("phoneNumber")).thenReturn("1234567890");
        when(mockResultSet.getInt("borrowed_books")).thenReturn(3);

        List<Client> clients = userDao.getAllClients();

        assertNotNull(clients);
        assertEquals(1, clients.size());
        Client client = clients.get(0);
        assertEquals("clientUser", client.getUsername());
        assertEquals("client@email.com", client.getEmail());
        assertEquals(25, client.getAge());
        assertEquals("1234567890", client.getPhoneNumber());
        assertEquals(3, client.getBorrowedBook());
    }
}
