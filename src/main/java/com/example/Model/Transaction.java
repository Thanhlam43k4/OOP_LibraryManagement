package com.example.Model;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

/**
 * Represents a transaction where a user borrows a document.
 */
public class Transaction {
    private int transactionId;       // Unique identifier for the transaction
    private int userId;              // Unique identifier for the user who borrowed the document
    private String ISBN;             // ISBN of the borrowed document
    private Date borrowedDate;       // Date when the document was borrowed
    private Date returnDate;         // Expected return date of the document
    private Date actualReturnDate;   // Actual return date of the document

    /**
     * Default constructor.
     */
    public Transaction() {
    }

    /**
     * Constructor with parameters to initialize all fields.
     *
     * @param transactionId    Unique identifier for the transaction
     * @param userId           Unique identifier for the user
     * @param ISBN             ISBN of the borrowed document
     * @param borrowedDate     Date when the document was borrowed
     * @param returnDate       Expected return date of the document
     */
    public Transaction(int transactionId, int userId, String ISBN,
                       Date borrowedDate, Date returnDate) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.ISBN = ISBN;
        this.borrowedDate = borrowedDate;
        this.returnDate = returnDate;
    }

    /**
     * Constructor with parameters to initialize the user and document information.
     *
     * @param userId       Unique identifier for the user
     * @param ISBN         ISBN of the borrowed document
     * @param borrowedDate Date when the document was borrowed
     * @param returnDate   Expected return date of the document
     */
    public Transaction(int userId, String ISBN, Date borrowedDate, Date returnDate) {
        this.userId = userId;
        this.ISBN = ISBN;
        this.borrowedDate = borrowedDate;
        this.returnDate = returnDate;
    }

    /**
     * Constructor with parameters to initialize the user and document information
     * and set the borrowing date to the current time and return date to 14 days later.
     *
     * @param userId Unique identifier for the user
     * @param ISBN   ISBN of the borrowed document
     */
    public Transaction(int userId, String ISBN) {
        this.userId = userId;
        this.ISBN = ISBN;

        // Set borrowedDate to the current date
        this.borrowedDate = new Date(System.currentTimeMillis());

        // Calculate returnDate as 14 days from the borrowedDate
        this.returnDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(14));
    }

    /**
     * Constructor to initialize a transaction with full details, including actual return date.
     *
     * @param transactionId    Unique identifier for the transaction
     * @param userId           Unique identifier for the user
     * @param ISBN             ISBN of the borrowed document
     * @param borrowedDate     Date when the document was borrowed
     * @param returnDate       Expected return date of the document
     * @param actualReturnDate Actual date when the document was returned
     */
    public Transaction(int transactionId, int userId, String ISBN, Date borrowedDate,
                       Date returnDate, Date actualReturnDate) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.ISBN = ISBN;
        this.borrowedDate = borrowedDate;
        this.returnDate = returnDate;
        this.actualReturnDate = actualReturnDate;
    }

    // Getters and Setters

    /**
     * Gets the transaction ID.
     *
     * @return the transaction ID
     */
    public int getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the transaction ID.
     *
     * @param transactionId the transaction ID to set
     */
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Gets the user ID.
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the ISBN of the borrowed document.
     *
     * @return the ISBN
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * Sets the ISBN of the borrowed document.
     *
     * @param ISBN the ISBN to set
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * Gets the borrowed date.
     *
     * @return the borrowed date
     */
    public Date getBorrowedDate() {
        return borrowedDate;
    }

    /**
     * Sets the borrowed date.
     *
     * @param borrowedDate the borrowed date to set
     */
    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    /**
     * Gets the return date.
     *
     * @return the return date
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date.
     *
     * @param returnDate the return date to set
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Gets the actual return date.
     *
     * @return the actual return date
     */
    public Date getActualReturnDate() {
        return actualReturnDate;
    }

    /**
     * Sets the actual return date.
     *
     * @param actualReturnDate the actual return date to set
     */
    public void setActualReturnDate(Date actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    /**
     * Provides a string representation of the transaction.
     *
     * @return a string representing the transaction
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", userId=" + userId +
                ", ISBN='" + ISBN + '\'' +
                ", borrowedDate=" + borrowedDate +
                ", returnDate=" + returnDate +
                ", actualReturnDate=" + actualReturnDate +
                '}';
    }
}
