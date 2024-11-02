package com.example.Model;

public class Client extends User {
    private int borrowedBook;


    public Client(String email, String password) {
        super(email, password);
    }
    public Client(int id, String username, String email,int age,String phoneNumber, int borrowedBook){
        super(id,username,email,age,phoneNumber);
        this.borrowedBook = borrowedBook;
    }
    public Client(String email, String username, String phoneNumber,int age){
        super(email,username,phoneNumber,age);
    }
    public int getBorrowedBook() {
        return borrowedBook;
    }

    public void setBorrowedBook(int borrowedBook) {
        this.borrowedBook = borrowedBook;
    }

    @Override
    public String getRole() {
        return "client";
    }


}
