package com.example.grzegorzkokoszka.shopproject;

/**
 * Created by Grzegorz Kokoszka on 2017-02-23.
 */

public class Payment {
    private String id;
    private String Name;

    public Payment(String id, String name) {
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
