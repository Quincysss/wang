package com.example.test;

public class Food {
    double caloricAmount;
    String category;
    double fat;
    int foodid;
    String foodname;
    double servingAmount;
    String servingUnit;

    public Food(double caloricAmount,String fruit,double fat,int foodid,String foodname,double servingAmount,String servingUnit)
    {
        this.caloricAmount = caloricAmount;
        this.category = fruit;
        this.fat = fat;
        this.foodid = foodid;
        this.foodname = foodname;
        this.servingAmount = servingAmount;
        this.servingUnit = servingUnit;
    }

    public double getCaloricAmount(){return caloricAmount;}
    public String getCategory(){return category;}
    public double getFat(){return fat;}
    public int getFoodid(){return foodid;}
    public String getFoodname(){return foodname;}
    public double getServingAmount(){return servingAmount;}
    public String getServingUnit(){return servingUnit;}
    public void setCaloricAmount(double caloricAmount) {this.caloricAmount = caloricAmount;}
    public void setCategory(String category){this.category = category;}
    public void setFat(double fat){this.fat = fat;}
    public void setFoodid(int foodid){this.foodid = foodid;}
    public void setServingAmount(double servingAmount){this.servingAmount = servingAmount;}
    public void setServingUnit(String servingUnit){this.servingUnit = servingUnit;}
}
