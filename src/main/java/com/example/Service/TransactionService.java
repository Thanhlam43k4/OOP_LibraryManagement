package com.example.Service;

import com.example.DAO.TransactionDaoImpl;
import com.example.Interface.TransactionDao;
import com.example.Model.Transaction;

import java.sql.Connection;
import java.util.List;

/**
 * The TransactionService class provides business logic for managing transactions.
 * It handles operations such as borrowing and returning books, retrieving transactions by user,
 * and getting a list of all transactions.
 * This service interacts with the TransactionDao to perform CRUD operations on the transaction data.
 */
public class TransactionService {

    // Singleton instance of TransactionService
    public static TransactionService instance;

    // TransactionDao instance for performing database operations
    private final TransactionDao transactionDao;

    /**
     * Private constructor to initialize TransactionService with a given database connection.
     * This constructor is used to inject the database connection for the DAO operations.
     *
     * @param con The database connection to be used by the service
     */
    public TransactionService(Connection con) {
        transactionDao = new TransactionDaoImpl(con);
    }

    /**
     * Retrieves a list of transactions associated with a specific user ID.
     *
     * @param userId The ID of the user whose transactions are to be retrieved
     * @return A list of transactions associated with the specified user
     */
    public List<Transaction> getTransactionsByUserId(int userId) {
        return transactionDao.getTransactionsByUserId(userId);
    }

    /**
     * Allows a user to borrow a book by creating a transaction entry for the borrow action.
     * The transaction is recorded in the database.
     *
     * @param userId The ID of the user borrowing the book
     * @param ISBN The ISBN of the book being borrowed
     */
    public void borrowBook(int userId, String ISBN) {
        transactionDao.addTransaction(userId, ISBN);
    }

    /**
     * Retrieves a list of all transactions in the system.
     *
     * @return A list of all transactions
     */
    public List<Transaction> getAllTransaction() {
        return transactionDao.getAllTransaction();
    }

    /**
     * Allows a user to return a borrowed book by creating a return transaction entry.
     * The transaction is updated in the database.
     *
     * @param userId The ID of the user returning the book
     * @param ISBN The ISBN of the book being returned
     */
    public void returnBook(int userId, String ISBN) {
        transactionDao.returnBook(userId, ISBN);
    }

}
