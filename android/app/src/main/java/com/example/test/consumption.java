package com.example.test;

import com.google.gson.JsonObject;

public class consumption {
    JsonObject consumptionPK;
    JsonObject food;
    double quantityOrServings;
    JsonObject users;

    public consumption(JsonObject consumptionPK,JsonObject food,double quantityOrServings,JsonObject users) {
        this.consumptionPK = consumptionPK;
        this.food = food;
        this.users = users;
        this.quantityOrServings = quantityOrServings;
    }
}
