package com.example.test;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

@Entity
public class StepData {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "step")
    public int step;
    @ColumnInfo(name = "user")
    public int user;
    @ColumnInfo(name = "time")
    @TypeConverters(DateConvert.class)
    public Date time;

    public StepData(int step,int user,Date time)
    {
        this.step = step;
        this.user = user;
        this.time = time;
    }
    public int getId()
    {
        return id;
    }
    public int getStep()
    {
        return step;
    }
    public Date getTime()
    {
        return time;
    }
    public int getUserId(){return user;}
    public void setId(int id)
    {
        this.id = id;
    }
    public void setStep(int step)
    {
        this.step = step;
    }
    public void setTime(Date time)
    {
        this.time = time;
    }
    public void setUserId(int userId){this.user = userId;}
}
