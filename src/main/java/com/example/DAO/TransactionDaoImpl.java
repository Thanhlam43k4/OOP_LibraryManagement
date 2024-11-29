package com.example.DAO;

import com.example.Interface.TransactionDao;
import com.example.Model.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of TransactionDao interface for handling transaction-related database operations.
 */
public class TransactionDaoImpl implements TransactionDao {

    private final Connection con;

    /**
     * Constructor that initializes the database connection.
     *
     * @param con the database connection
     */
    public TransactionDaoImpl(Connection con) {
        this.con = con;
    }

    /**
     * Adds a new transaction with given userId and ISBN.
     * Automatically sets borrowed date to the current date and return date to 14 days from the current date.
     *
     * @param userId the user ID
     * @param ISBN   the ISBN of the book
     */
    @Override
    public void addTransaction(int userId, String ISBN) {
        String sql = "INSERT INTO transactions (user_id, copy_ISBN, borrowed_date, return_date) VALUES (?, ?, ?, ?)";
        String updateStatusSQL = "UPDATE copies SET status = 'Checked Out' WHERE copy_ISBN = ?";
        String updateBorrowedBookSql = "UPDATE client SET borrowed_books = borrowed_books + 1 WHERE user_id = ?";
        LocalDate borrowedDate = LocalDate.now();
        LocalDate returnDate = borrowedDate.plusDays(14);

        try (PreparedStatement pstmt = con.prepareStatement(sql);
             PreparedStatement updateStatusStmt = con.prepareStatement(updateStatusSQL);
             PreparedStatement updateBorrowedBookStmt = con.prepareStatement(updateBorrowedBookSql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, ISBN);
            pstmt.setDate(3, Date.valueOf(borrowedDate));
            pstmt.setDate(4, Date.valueOf(returnDate));
            pstmt.executeUpdate();

            System.out.println("Add Transaction Successfully with userId: " + userId + " ISBN: " + ISBN);

            updateStatusStmt.setString(1, ISBN);
            updateStatusStmt.executeUpdate();
            System.out.println("Status of Copy with ISBN=" + ISBN + " is changed to Checked Out");

            updateBorrowedBookStmt.setInt(1, userId);
            updateBorrowedBookStmt.executeUpdate();
            System.out.println("Updated borrowed_book for userId: " + userId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all transactions for a given user ID.
     *
     * @param userId the user ID
     * @return a list of transactions for the specified user
     */
    @Override
    public List<Transaction> getTransactionsByUserId(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getDate("actual_return_date") == null) { // Check if the book is not yet returned
                    Transaction transaction = new Transaction(
                            rs.getInt("transaction_id"),
                            rs.getInt("user_id"),
                            rs.getString("copy_ISBN"),
                            rs.getDate("borrowed_date"),
                            rs.getDate("return_date"),
                            rs.getDate("actual_return_date")
                    );
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    /**
     * Retrieves all transactions in the system.
     *
     * @return a list of all transactions
     */
    @Override
    public List<Transaction> getAllTransaction() {
        List<Transaction> trans = new ArrayList<>();
        String sql = "SELECT * FROM transactions";

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("user_id"),
                        rs.getString("copy_ISBN"),
                        rs.getDate("borrowed_date"),
                        rs.getDate("return_date"),
                        rs.getDate("actual_return_date")
                );
                trans.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trans;
    }



    /**
     * Marks a book as returned by updating the transaction and book status.
     *
     * @param userId the user ID
     * @param ISBN   the ISBN of the book
     */
    @Override
    public void returnBook(int userId, String ISBN) {
        String updateTransactionSQL = "UPDATE transactions SET actual_return_date = CURRENT_DATE " +
                "WHERE user_id = ? AND copy_ISBN = ? AND actual_return_date IS NULL";
        String updateCopySQL = "UPDATE copies SET status = 'Available' WHERE copy_ISBN = ?";
        String updateUserSql = "UPDATE client SET borrowed_books = borrowed_books - 1 WHERE user_id = ?";

        try (PreparedStatement updateTransactionStmt = con.prepareStatement(updateTransactionSQL);
             PreparedStatement updateCopyStmt = con.prepareStatement(updateCopySQL);
             PreparedStatement updateUserStmt = con.prepareStatement(updateUserSql)) {

            updateTransactionStmt.setInt(1, userId);
            updateTransactionStmt.setString(2, ISBN);
            int rowsUpdated = updateTransactionStmt.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("No active transaction found for userId: " + userId + " and ISBN: " + ISBN);
                return;
            }

            updateCopyStmt.setString(1, ISBN);
            updateCopyStmt.executeUpdate();

            updateUserStmt.setInt(1, userId);
            updateUserStmt.executeUpdate();

            System.out.println("Book returned successfully. ISBN: " + ISBN + " is now available.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
