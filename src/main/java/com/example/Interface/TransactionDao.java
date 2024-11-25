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
     * Deletes a transaction from the system.
     *
     * @param transactionId The ID of the transaction to delete.
     */
    public void deleteTransaction(int transactionId);

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
    public List<Transaction> getAllTransaction();

    /**
     * Marks a document as returned by a user, updating the transaction.
     *
     * @param userId The ID of the user returning the document.
     * @param ISBN   The ISBN of the document being returned.
     */
    public void returnBook(int userId, String ISBN);

    /**
     * Retrieves a transaction by its ID.
     *
     * @param transactionId The ID of the transaction to retrieve.
     * @return The transaction with the specified ID, or {@code null} if not found.
     */
    Transaction getTransactionById(int transactionId);

    /**
     * Updates the return date for a specific transaction.
     *
     * @param transactionId The ID of the transaction to update.
     * @param returnDate   The new return date to set.
     */
    void updateReturnDate(int transactionId, LocalDate returnDate);

    /**
     * Retrieves all overdue transactions where the return date has passed.
     *
     * @return A list of overdue transactions.
     */
    List<Transaction> getOverdueTransactions();
}
