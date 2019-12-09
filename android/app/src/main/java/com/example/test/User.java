package com.example.test;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

public class User {
    private int userid;
    private String username;
    private String surname;
    private String email;
    private String dob;
    private double height;
    private double weight;
    private String gender;
    private String address;
    private int postcode;
    private int levelActivity;
    private int stepsPermile;

    public User(int id,String username,String surname,String email,String dob,double height,double weight,
                String gender,String address,int postcode ,int levelActivity,int stepsPermile)
    {
        this.userid = id;
        this.username = username;
        this.surname = surname;
        this.email = email;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.address = address;
        this.postcode = postcode;
        this.levelActivity = levelActivity;
        this.stepsPermile = stepsPermile;
    }
    public int getUserid()
    {
        return userid;
    }
    public String getUsername(){return username;}
    public String getSurname(){return surname;}
    public String getEmail(){return email;}
    public String getGender(){return gender;}
    public String getAddress(){return address;}
    public int getPostcode(){return postcode;}
    public int getLevelActivity(){return levelActivity;}
    public int getStepsPermile(){return stepsPermile;}
    public void setUserid(int id){this.userid = id;}
    public void setUsername(String username){this.username = username;}
    public void setSurname(String surname){this.surname = surname;}
    public void setEmail(String email){this.email = email;}
    public void setDob(String dob){this.dob = dob;}
    public void setHeight(double height){this.height = height;}
    public void setWeight(double weight){this.weight = weight;}
    public void setGender(String gender){this.gender = gender;}
    public void setAddress(String address){this.address = address;}
    public void setPostcode(int postcode){this.postcode = postcode;}
    public void setLevelActivity(int levelActivity){this.levelActivity = levelActivity;}
    public void setStepsPermile(int steps_permile){this.stepsPermile = steps_permile;}
}
