package com.example.Service;

import com.example.DAO.TransactionDaoImpl;
import com.example.Interface.TransactionDao;
import com.example.Model.Transaction;

import java.sql.Connection;
import java.util.List;

public class TransactionService {
    public static TransactionService instance;
    private TransactionDao transactionDao;

    public TransactionService(Connection con){
        transactionDao = new TransactionDaoImpl(con);
    }
    public void borrowBook(Transaction transaction){
        transactionDao.addTransaction(transaction);
    }
    public List<Transaction> getTransactionsByUserId(int userId){
        return  transactionDao.getTransactionsByUserId(userId);
    }

    public void returnBook(int transactionId){
        transactionDao.deleteTransaction(transactionId);
    }
}
