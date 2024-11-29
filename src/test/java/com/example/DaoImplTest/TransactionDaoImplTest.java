package com.example.DaoImplTest;
import com.example.DAO.*;
import com.example.Model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionDaoImplTest {

    @Mock
    private Connection con;
    @Mock
    private PreparedStatement pstmt;
    @Mock
    private ResultSet rs;

    private TransactionDaoImpl transactionDao;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        transactionDao = new TransactionDaoImpl(con);
    }


    @Test
    public void testAddTransactionWithUserIdAndISBN() throws SQLException {
        int userId = 1;
        String ISBN = "1234567890";
        String sql = "INSERT INTO transactions (user_id, copy_ISBN, borrowed_date, return_date) VALUES (?, ?, ?, ?)";
        String updateStatusSQL = "UPDATE copies SET status = 'Checked Out' WHERE copy_ISBN = ?";
        String updateBorrowedBookSql = "UPDATE client SET borrowed_books = borrowed_books + 1 WHERE user_id = ?";

        // Mock PreparedStatements
        Mockito.when(con.prepareStatement(sql)).thenReturn(pstmt);
        Mockito.when(con.prepareStatement(updateStatusSQL)).thenReturn(pstmt);
        Mockito.when(con.prepareStatement(updateBorrowedBookSql)).thenReturn(pstmt);

        // Mock executeUpdate to return the number of affected rows (let's assume it returns 1 for all)
        Mockito.when(pstmt.executeUpdate()).thenReturn(1);

        // Test the addTransaction method
        transactionDao.addTransaction(userId, ISBN);

        // Verify that executeUpdate was called the correct number of times
        Mockito.verify(pstmt, Mockito.times(3)).executeUpdate();
    }


    @Test
    public void testGetTransactionsByUserId() throws SQLException {
        int userId = 1;
        String sql = "SELECT * FROM transactions WHERE user_id = ?";
        Mockito.when(con.prepareStatement(sql)).thenReturn(pstmt);

        // Mock the ResultSet
        Mockito.when(pstmt.executeQuery()).thenReturn(rs);
        Mockito.when(rs.next()).thenReturn(true).thenReturn(false);  // Only one result

        // Mock the values returned by the ResultSet
        Mockito.when(rs.getInt("transaction_id")).thenReturn(1);
        Mockito.when(rs.getInt("user_id")).thenReturn(userId);
        Mockito.when(rs.getString("copy_ISBN")).thenReturn("1234567890");
        Mockito.when(rs.getDate("borrowed_date")).thenReturn(Date.valueOf(LocalDate.now()));
        Mockito.when(rs.getDate("return_date")).thenReturn(Date.valueOf(LocalDate.now().plusDays(14)));
        Mockito.when(rs.getDate("actual_return_date")).thenReturn(null);

        // Test the getTransactionsByUserId method
        List<Transaction> transactions = transactionDao.getTransactionsByUserId(userId);
        assertNotNull(transactions);
        assertEquals(1, transactions.size());
        assertEquals(userId, transactions.get(0).getUserId());
    }

    @Test
    public void testGetAllTransaction() throws SQLException {
        String sql = "SELECT * FROM transactions";
        Mockito.when(con.createStatement()).thenReturn(Mockito.mock(Statement.class));
        Mockito.when(con.createStatement().executeQuery(sql)).thenReturn(rs);

        // Mock ResultSet behavior
        Mockito.when(rs.next()).thenReturn(true).thenReturn(false);  // Only one result
        Mockito.when(rs.getInt("transaction_id")).thenReturn(1);
        Mockito.when(rs.getInt("user_id")).thenReturn(1);
        Mockito.when(rs.getString("copy_ISBN")).thenReturn("1234567890");
        Mockito.when(rs.getDate("borrowed_date")).thenReturn(Date.valueOf(LocalDate.now()));
        Mockito.when(rs.getDate("return_date")).thenReturn(Date.valueOf(LocalDate.now().plusDays(14)));
        Mockito.when(rs.getDate("actual_return_date")).thenReturn(null);

        // Test the getAllTransaction method
        List<Transaction> transactions = transactionDao.getAllTransaction();
        assertNotNull(transactions);
        assertEquals(1, transactions.size());
    }

    @Test
    public void testReturnBook() throws SQLException {
        int userId = 1;
        String ISBN = "1234567890";
        String updateTransactionSQL = "UPDATE transactions SET actual_return_date = CURRENT_DATE WHERE user_id = ? AND copy_ISBN = ? AND actual_return_date IS NULL";
        String updateCopySQL = "UPDATE copies SET status = 'Available' WHERE copy_ISBN = ?";
        String updateUserSql = "UPDATE client SET borrowed_books = borrowed_books - 1 WHERE user_id = ?";

        // Mock PreparedStatements
        Mockito.when(con.prepareStatement(updateTransactionSQL)).thenReturn(pstmt);
        Mockito.when(con.prepareStatement(updateCopySQL)).thenReturn(pstmt);
        Mockito.when(con.prepareStatement(updateUserSql)).thenReturn(pstmt);

        // Mock executeUpdate to return the number of affected rows (let's assume it returns 1 for all)
        Mockito.when(pstmt.executeUpdate()).thenReturn(1);

        // Test the returnBook method
        transactionDao.returnBook(userId, ISBN);

        // Verify that executeUpdate was called three times
        Mockito.verify(pstmt, Mockito.times(3)).executeUpdate();
    }

}
