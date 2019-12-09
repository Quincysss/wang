package com.example.test;

import java.util.Date;

public class Account {
    private int credientalid;
    private String username;
    private String passwordHash;
    private String signUpDate;
    private User userid;

    public Account(int credientalid,String username,String passwordHash,String signUpDate,User user)
    {
        this.credientalid = credientalid;
        this.username = username;
        this.passwordHash = passwordHash;
        this.signUpDate = signUpDate;
        this.userid = user;
    }

    public int getCredientalid(){return credientalid;}
    public String getUsername(){return username;}
    public String getSignUpDate(){ return signUpDate;}
    public User getUserid(){return userid;}
    public String getPasswordHash(){return passwordHash;}
    public void setCredientalid(int id){credientalid = id;}
    public void setUsername(String username){this.username = username;}
    public void setPasswordHash(String passwordHash){this.passwordHash = passwordHash;}
    public void setSignUpDate(String date){this.signUpDate = date;}
    public void setUser(User user){this.userid = user;}
}
