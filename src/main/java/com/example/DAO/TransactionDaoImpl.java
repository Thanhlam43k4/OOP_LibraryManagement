package com.example.DAO;
import com.example.Interface.TransactionDao;
import com.example.Model.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {
    private Connection con;

    public TransactionDaoImpl(Connection con){
        this.con = con;
    }

    @Override
    public void addTransaction(Transaction transaction){
        String sql = "INSERT INTO transactions (user_id,copy_ISBN,borrowed_date,return_date) " +
                "VALUES (?, ?, ?, ?)";
        try(PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setInt(1,transaction.getUserId());
            pstmt.setString(2,transaction.getISBN());
            pstmt.setDate(3,transaction.getBorrowedDate());
            pstmt.setDate(4,transaction.getReturnDate());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void addTransaction(int userId, String ISBN) {
        String sql = "INSERT INTO transactions (user_id,copy_ISBN,borrowed_date,return_date)" +
                "VALUES (?, ?, ?, ?)";
        String updateStatusSQL = "UPDATE copies SET status = 'Checked Out' WHERE copy_ISBN = ?";

        LocalDate borrowedDate = LocalDate.now();
        // Cộng thêm 14 ngày để lấy ngày trả
        LocalDate returnDate = borrowedDate.plusDays(14);
        try(PreparedStatement pstmt = con.prepareStatement(sql);
            PreparedStatement updatestmt = con.prepareStatement(updateStatusSQL)) {
            pstmt.setInt(1,userId);
            pstmt.setString(2,ISBN);
            pstmt.setDate(3, Date.valueOf(borrowedDate));
            pstmt.setDate(4,Date.valueOf(returnDate));
            pstmt.executeUpdate();
            System.out.println("Add Transaction Successfully with userId: "+ userId +" ISBN: " +ISBN);

            updatestmt.setString(1,ISBN);
            updatestmt.executeUpdate();

            System.out.println("Status of Copy with ISBN=" +ISBN + " is changed to Checked Out");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Transaction> getTransactionsByUserId(int userId){
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ?";
        try(PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setInt(1,userId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Transaction transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("user_id"),
                        rs.getString("copy_ISBN"),
                        rs.getDate("borrowed_date"),
                        rs.getDate("return_date")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public void deleteTransaction(int transactionId){
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";
        try(PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
