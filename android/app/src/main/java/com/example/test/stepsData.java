package com.example.test;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

@Entity
public class stepsData {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "step")
    public double step;
    @ColumnInfo(name = "time")
    @TypeConverters(DateConvert.class)
    public Date time;

    public stepsData(double step,Date time)
    {
        this.step = step;
        this.time = time;
    }
    public int getId()
    {
        return id;
    }
    public double getStep()
    {
        return step;
    }
    public Date getTime()
    {
        return time;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public void setStep(double step)
    {
        this.step = step;
    }
    public void setTime(Date time)
    {
        this.time = time;
    }
}
