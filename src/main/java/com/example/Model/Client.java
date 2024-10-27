package com.example.Model;

public class Client extends User {
    private int borrowed_book;


    public Client(String email, String password) {
        super(email, password);
    }
    public Client(String email,String password, int borrowed_book){
        super(email,password);
        this.borrowed_book = borrowed_book;
    }
}
