package com.example.Interface;

import com.example.Model.Transaction;

import java.time.LocalDate;
import java.util.List;

/**
 * This interface defines the data access methods for managing transactions related to documents (e.g., book borrowings).
 * It provides CRUD operations (Create, Read, Update, Delete) for transactions and additional operations
 * like handling overdue transactions, updating return dates, and retrieving transactions by user or document.
 */
public interface TransactionDao {

    /**
     * Adds a new transaction to the system.
     *
     * @param transaction The transaction to be added.
     */
    public void addTransaction(Transaction transaction);

    /**
     * Retrieves all transactions for a specific user.
     *
     * @param userId The ID of the user to retrieve transactions for.
     * @return A list of transactions for the specified user.
     */
    public List<Transaction> getTransactionsByUserId(int userId);


    /**
     * Adds a transaction for a user borrowing a document identified by its ISBN.
     *
     * @param userId The ID of the user borrowing the document.
     * @param ISBN   The ISBN of the document being borrowed.
     */
    public void addTransaction(int userId, String ISBN);

    /**
     * Retrieves all transactions in the system.
     *
     * @return A list of all transactions.
     */
    List<Transaction> getAllTransaction();

    /**
     * Marks a document as returned by a user, updating the transaction.
     *
     * @param userId The ID of the user returning the document.
     * @param copyISBN   The ISBN of the document being returned.
     */
    public void returnBook(int userId, String copyISBN);

}
