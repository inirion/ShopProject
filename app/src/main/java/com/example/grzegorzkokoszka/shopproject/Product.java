package com.example.grzegorzkokoszka.shopproject;
import java.util.Comparator;
import android.media.Image;

import java.util.Date;

/**
 * Created by Grzegorz Kokoszka on 2016-12-27.
 */

public class Product{
    private float price;
    private String name;
    private String description;
    private int pictureID;

    Product(String name, String description, int picture, float price){
        this.name = name;
        this.description = description;
        this.pictureID = picture;
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public int getPicture() {
        return pictureID;
    }

    public void setPicture(int picture) {
        this.pictureID = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
