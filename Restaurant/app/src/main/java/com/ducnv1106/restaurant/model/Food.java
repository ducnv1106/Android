package com.ducnv1106.restaurant.model;

public class Food extends BaseModel {
    private String nameFood;
    private String description;
    private  int price;
    private int img;

    public Food(String nameFood, String description,int price , int img) {
        this.nameFood = nameFood;
        this.description = description;
        this.price = price;
        this.img = img;
    }

}
