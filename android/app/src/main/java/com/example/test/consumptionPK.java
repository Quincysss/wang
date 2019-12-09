package com.example.test;

public class consumptionPK {
    String date;
    int foodid;
    int userid;

    public consumptionPK(String date,int foodid,int userid)
    {
        this.date = date;
        this.foodid = foodid;
        this.userid = userid;
    }

    public String getDate(){return date;}
    public int getFoodid(){return foodid;}
    public int getUserid(){return userid;}
    public void setDate(String date){this.date = date;}
    public void setFoodid(int foodid){this.foodid = foodid;}
    public void setUserid(int userid){this.userid = userid;}
}
