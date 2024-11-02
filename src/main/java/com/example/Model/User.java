package com.example.Model;

import java.sql.Date;

public  class User {
    private int id;
    private String username;
    private String email;
    private int age;
    private String password;
    private String phoneNumber;
    private Date dob;
    private String role;


    public User(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public User(int id,String email,String username,int age,String phoneNumber){
        this.id = id;
        this.email = email;
        this.username = username;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }
    public User(int id,String email, String username, String role){
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;

    }
    public User(String email,String username, String phoneNumber,int age){
        this.email = email;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.age =  age;
    }



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
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
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
    public String getRole() // Phương thức trừu tượng để lấy vai trò
    {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }


}
