package com.example.test;

import com.google.gson.JsonObject;

public class report {
    double calorieGoal;
    reportPK reportPK;
    double totalCaloriesConsumed;
    double totalCaloriesBurned;
    int totalStepsTaken;
    JsonObject users;

    public report(double calorieGoal,reportPK a,double b,double c,int d,JsonObject users)
    {
        this.calorieGoal = calorieGoal;
        this.reportPK = a;
        this.totalCaloriesConsumed = b;
        this.totalCaloriesBurned = c;
        this.totalStepsTaken = d;
        this.users = users;
    }

    public double getCalorieGoal(){return calorieGoal;}
    public reportPK getReportPK(){return reportPK;}
    public double getTotalCaloriesBurned(){return totalCaloriesBurned;}
    public double getTotalCaloriesConsumed(){return totalCaloriesConsumed;}
    public int getTotalStepsTaken(){return totalStepsTaken;}
    public JsonObject getUsers(){return users;}
    public void setCalorieGoal(double calorieGoal){this.calorieGoal = calorieGoal;}
    public void setReportPK(reportPK p){this.reportPK = p;}
    public void setTotalCaloriesBurned(double totalCaloriesBurned){this.totalCaloriesBurned = totalCaloriesBurned;}
    public void setTotalCaloriesConsumed(double totalCaloriesConsumed){this.totalCaloriesConsumed = totalCaloriesConsumed;}
    public void setTotalStepsTaken (int totalStepsTaken){this.totalStepsTaken = totalStepsTaken;}
    public void setUsers(JsonObject user){ this.users = user;}

}
