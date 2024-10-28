package com.example.Model;

public class Client extends User {
    private int borrowedBook;


    public Client(String email, String password) {
        super(email, password);
    }
    public Client(String email,String password, int borrowedBook){
        super(email,password);
        this.borrowedBook = borrowedBook;
    }
    public Client(String email, String username, String phoneNumber,int age){
        super(email,username,phoneNumber,age);
    }
    public int getBorrowed_book() {
        return borrowedBook;
    }

    public void setBorrowed_book(int borrowedBook) {
        this.borrowedBook = borrowedBook;
    }




}
