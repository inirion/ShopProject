package com.example.grzegorzkokoszka.shopproject;
import java.util.ArrayList;
import java.util.Comparator;
import android.media.Image;

import java.util.Date;
import java.util.List;

/**
 * Created by Grzegorz Kokoszka on 2016-12-27.
 */

public class Product{
    private float price;
    private String name;
    private String description;
    private int pictureID;
    private String time;
    private static int count;
    private String auctionId;
    private String authorId;
    private boolean finished;

    public String getHighestBitterId() {
        return highestBitterId;
    }

    private String highestBitterId;
    public String getAuctionId(){return auctionId;}

    public String getAuthorId() {
        return authorId;
    }




    Product(String name, String description, int picture, float price, String author, String auctionId,String highestBitterId,String time,boolean finished){
        this.name = name;
        this.description = description;
        this.pictureID = picture;
        this.price = price;
        this.authorId = author;
        this.auctionId= auctionId;
        this.highestBitterId = highestBitterId;
        this.time = time;
        this.finished=finished;
    }
    public boolean isFinished(){return finished;}
    public static void setCount(int c){count = c;}
    public static int getCount(){return count;}

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

    public int getPictureID() {
        return pictureID;
    }

    public void setPictureID(int pictureID) {
        this.pictureID = pictureID;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
