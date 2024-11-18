package com.example.Interface;

import com.example.Model.Transaction;

import java.util.List;

public interface TransactionDao {
    public void addTransaction(Transaction transaction);
    public List<Transaction> getTransactionsByUserId(int userId);
    public void deleteTransaction(int transactionId);
    public void addTransaction(int userId, String ISBN);
    public List<Transaction> getAllTransaction();
    public void returnBook(int userId,String ISBN);
}