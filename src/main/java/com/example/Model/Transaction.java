package com.example.Model;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

public class Transaction {
    private int transactionId;       // transaction_id
    private int userId;              // user_id
    private String ISBN;         // document_id
    private Date borrowedDate;       // borrowed_date
    private Date returnDate;         // return_date
    private Date actualReturnDate;   // actual_return_date

    public Transaction() {
    }
    // Constructor with parameters
    public Transaction(int transactionId, int userId, String ISBN,
                       Date borrowedDate, Date returnDate) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.ISBN = ISBN;
        this.borrowedDate = borrowedDate;
        this.returnDate = returnDate;
    }

    public Transaction( int userId, String ISBN , Date borrowedDate, Date returnDate) {
        this.userId = userId;
        this.ISBN = ISBN;
        this.borrowedDate = borrowedDate;
        this.returnDate = returnDate;
    }
    public Transaction(int userId, String ISBN) {
        this.userId = userId;
        this.ISBN = ISBN;

        // Lấy thời điểm hiện tại (borrowedDate là thời điểm hiện tại)
        this.borrowedDate = new Date(System.currentTimeMillis());


        // Tính toán ngày trả sau 14 ngày từ thời điểm hiện tại
        this.returnDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(14));
    }

    public Transaction(int transactionId, int userId, String copyIsbn, Date borrowedDate, Date returnDate, Date actualReturnDate) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.ISBN = copyIsbn;
        this.borrowedDate = borrowedDate;
        this.returnDate = returnDate;
        this.actualReturnDate = actualReturnDate;
    }


    public int getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(Date actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    // Optional: Override toString for better representation
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", userId=" + userId +
                ", ISBN=" + ISBN +
                ", borrowedDate=" + borrowedDate +
                ", returnDate=" + returnDate +
                ", actualReturnDate=" + actualReturnDate +
                '}';
    }
}
