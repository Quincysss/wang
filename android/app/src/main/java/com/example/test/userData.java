package com.example.test;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

//userdatabase
@Entity
public class userData {
    @NonNull
    @PrimaryKey
    public String username;
    @ColumnInfo(name = "password")
    public String password;


    public userData(String username,String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public void setUsername(String username){this.username = username;}
    public void setPassword(String password){this.password = password;}
}
