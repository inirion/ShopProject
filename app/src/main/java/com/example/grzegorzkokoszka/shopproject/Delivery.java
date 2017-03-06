package com.example.grzegorzkokoszka.shopproject;

/**
 * Created by Grzegorz Kokoszka on 2017-02-22.
 */

public class Delivery {
    private String id;
    private String Name;

    public Delivery(String id, String name) {
        this.id = id;
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
