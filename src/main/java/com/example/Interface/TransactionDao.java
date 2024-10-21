package com.example.Interface;

import com.example.Model.Transaction;

import java.util.List;

public interface TransactionDao {
    public void addTransaction(Transaction transaction);
    public List<Transaction> getTransactionsByUserId(int userId);
    public void deleteTransaction(int transactionId);

}