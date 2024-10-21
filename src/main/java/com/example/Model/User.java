package com.example.Model;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String email;
    private int age;
    private String password;
    private String phoneNumber;
    private  List<Transaction> transactions = new ArrayList<>();
    private Date dob;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public User(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void display()
    {
        System.out.println("UserName: " +  username +  " " + "Password: "+ password);
    }
}
