package com.example.test;

public class reportPK {
    String date;
    int userid;

    public reportPK(String date,int userid)
    {
        this.date = date;
        this.userid = userid;
    }

    public String getDate()
    {
        return date;
    }
    public int getUserid()
    {
        return userid;
    }
    public void setDate(String date)
    {
        this.date = date;
    }
    public void setUserid(int userid)
    {
        this.userid = userid;
    }
}
